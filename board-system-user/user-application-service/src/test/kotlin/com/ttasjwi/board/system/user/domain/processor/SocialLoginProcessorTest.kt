package com.ttasjwi.board.system.user.domain.processor

import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.user.domain.dto.SocialLoginCommand
import com.ttasjwi.board.system.user.domain.exception.OAuth2AuthorizationRequestNotFoundException
import com.ttasjwi.board.system.user.domain.model.SocialService
import com.ttasjwi.board.system.user.domain.model.fixture.googleOAuth2AuthorizationRequestFixture
import com.ttasjwi.board.system.user.domain.model.fixture.socialConnectionFixture
import com.ttasjwi.board.system.user.domain.model.fixture.userFixture
import com.ttasjwi.board.system.user.domain.port.fixture.*
import com.ttasjwi.board.system.user.domain.service.RefreshTokenHandler
import com.ttasjwi.board.system.user.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("[user-application-service] SocialLoginProcessor 테스트")
class SocialLoginProcessorTest {

    private lateinit var processor: SocialLoginProcessor
    private lateinit var oAuth2AuthorizationRequestPersistencePortFixture: OAuth2AuthorizationRequestPersistencePortFixture
    private lateinit var userRefreshTokenIdListPersistencePortFixture: UserRefreshTokenIdListPersistencePortFixture
    private lateinit var refreshTokenIdPersistencePortFixture: RefreshTokenIdPersistencePortFixture
    private lateinit var userPersistencePortFixture: UserPersistencePortFixture
    private lateinit var socialConnectionPersistencePortFixture: SocialConnectionPersistencePortFixture
    private lateinit var oAuth2AccessTokenClientPortFixture: OAuth2AccessTokenClientPortFixture
    private lateinit var oidcOAuth2UserPrincipalPortFixture: OidcOAuth2UserPrincipalPortFixture

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        processor = container.socialLoginProcessor
        oAuth2AuthorizationRequestPersistencePortFixture = container.oAuth2AuthorizationRequestPersistencePortFixture
        userRefreshTokenIdListPersistencePortFixture = container.userRefreshTokenIdListPersistencePortFixture
        refreshTokenIdPersistencePortFixture = container.refreshTokenIdPersistencePortFixture
        userPersistencePortFixture = container.userPersistencePortFixture
        socialConnectionPersistencePortFixture = container.socialConnectionPersistencePortFixture
        oAuth2AccessTokenClientPortFixture = container.oAuth2AccessTokenClientPortFixture
        oidcOAuth2UserPrincipalPortFixture = container.oidcOAuth2UserPrincipalPortFixture
    }

    @Test
    @DisplayName("소셜 연동에 해당하는 회원이 있으면, 로그인 처리한다.")
    fun test1() {
        // given
        val code = "code"
        val socialServiceName = "google"
        val socialServiceUserId = "google-user-id"
        val email = "hello@gmail.com"

        val oAuth2AuthorizationRequest = googleOAuth2AuthorizationRequestFixture()
        oAuth2AuthorizationRequestPersistencePortFixture.save(oAuth2AuthorizationRequest, expiresAt = appDateTimeFixture(minute = 16))

        val command = SocialLoginCommand(
            state = oAuth2AuthorizationRequest.state,
            code = code,
            currentTime = appDateTimeFixture(minute = 13)
        )


        val user = userPersistencePortFixture.save(
            userFixture(email = email)
        )

        socialConnectionPersistencePortFixture.save(
            socialConnectionFixture(
                socialConnectionId = 15567L,
                userId = user.userId,
                socialService = SocialService.GOOGLE,
                socialServiceUserId = socialServiceUserId
            )
        )

        oAuth2AccessTokenClientPortFixture.changeAccessToken("accessToken")
        oAuth2AccessTokenClientPortFixture.changeIdToken("IdToken")

        oidcOAuth2UserPrincipalPortFixture.changeSocialServiceName(socialServiceName)
        oidcOAuth2UserPrincipalPortFixture.changeSocialServiceUserId(socialServiceUserId)
        oidcOAuth2UserPrincipalPortFixture.changeEmail(email)


        // when
        val (createdUser, accessToken, refreshToken) = processor.socialLogin(command)

        // then
        val userTokenIds = userRefreshTokenIdListPersistencePortFixture.findAll(user.userId)
        val findOAuth2AuthorizationRequest = oAuth2AuthorizationRequestPersistencePortFixture.read(oAuth2AuthorizationRequest.state)

        assertThat(createdUser).isNull()
        assertThat(accessToken.authUser.userId).isEqualTo(user.userId)
        assertThat(accessToken.authUser.role).isEqualTo(user.role)
        assertThat(accessToken.tokenValue).isNotNull()
        assertThat(accessToken.issuedAt).isEqualTo(command.currentTime)
        assertThat(accessToken.expiresAt).isEqualTo(command.currentTime.plusMinutes(30))

        assertThat(refreshToken.userId).isEqualTo(user.userId)
        assertThat(refreshToken.refreshTokenId).isNotNull()
        assertThat(refreshToken.tokenValue).isNotNull()
        assertThat(refreshToken.issuedAt).isEqualTo(command.currentTime)
        assertThat(refreshToken.expiresAt).isEqualTo(command.currentTime.plusHours(RefreshTokenHandler.REFRESH_TOKEN_VALIDITY_HOUR))

        assertThat(userTokenIds).containsExactly(refreshToken.refreshTokenId)
        assertThat(refreshTokenIdPersistencePortFixture.exists(user.userId, refreshToken.refreshTokenId)).isTrue()
        assertThat(findOAuth2AuthorizationRequest).isNull()
    }

    @Test
    @DisplayName("소셜 연동은 없지만 이메일에 해당하는 회원이 있으면, 소셜 연동 생성 후 로그인 처리한다.")
    fun test2() {
        // given
        val code = "code"
        val socialServiceName = "google"
        val socialServiceUserId = "google-user-id"
        val email = "hello@gmail.com"

        val oAuth2AuthorizationRequest = googleOAuth2AuthorizationRequestFixture()
        oAuth2AuthorizationRequestPersistencePortFixture.save(oAuth2AuthorizationRequest, expiresAt = appDateTimeFixture(minute = 16))


        val command = SocialLoginCommand(
            state = oAuth2AuthorizationRequest.state,
            code = code,
            currentTime = appDateTimeFixture(minute = 13)
        )

        val user = userPersistencePortFixture.save(
            userFixture(email = email)
        )

        oAuth2AccessTokenClientPortFixture.changeAccessToken("accessToken")
        oAuth2AccessTokenClientPortFixture.changeIdToken("IdToken")

        oidcOAuth2UserPrincipalPortFixture.changeSocialServiceName(socialServiceName)
        oidcOAuth2UserPrincipalPortFixture.changeSocialServiceUserId(socialServiceUserId)
        oidcOAuth2UserPrincipalPortFixture.changeEmail(email)

        // when
        val (createdUser, accessToken, refreshToken) = processor.socialLogin(command)

        // then
        val userTokenIds = userRefreshTokenIdListPersistencePortFixture.findAll(user.userId)
        val findSocialConnection = socialConnectionPersistencePortFixture.read(
            socialService = SocialService.GOOGLE,
            socialServiceUserId = socialServiceUserId
        )!!
        val findOAuth2AuthorizationRequest = oAuth2AuthorizationRequestPersistencePortFixture.read(oAuth2AuthorizationRequest.state)

        assertThat(createdUser).isNull()
        assertThat(accessToken.authUser.userId).isEqualTo(user.userId)
        assertThat(accessToken.authUser.role).isEqualTo(user.role)
        assertThat(accessToken.tokenValue).isNotNull()
        assertThat(accessToken.issuedAt).isEqualTo(command.currentTime)
        assertThat(accessToken.expiresAt).isEqualTo(command.currentTime.plusMinutes(30))
        assertThat(refreshToken.userId).isEqualTo(user.userId)
        assertThat(refreshToken.refreshTokenId).isNotNull()
        assertThat(refreshToken.tokenValue).isNotNull()
        assertThat(refreshToken.issuedAt).isEqualTo(command.currentTime)
        assertThat(refreshToken.expiresAt).isEqualTo(command.currentTime.plusHours(RefreshTokenHandler.REFRESH_TOKEN_VALIDITY_HOUR))

        assertThat(userTokenIds).containsExactly(refreshToken.refreshTokenId)
        assertThat(refreshTokenIdPersistencePortFixture.exists(user.userId, refreshToken.refreshTokenId)).isTrue()

        assertThat(findSocialConnection.socialConnectionId).isNotNull
        assertThat(findSocialConnection.userId).isEqualTo(user.userId)
        assertThat(findSocialConnection.socialService).isEqualTo(SocialService.GOOGLE)
        assertThat(findSocialConnection.socialServiceUserId).isEqualTo(socialServiceUserId)
        assertThat(findSocialConnection.linkedAt).isEqualTo(command.currentTime)
        assertThat(findOAuth2AuthorizationRequest).isNull()
    }

    @Test
    @DisplayName("소셜 연동도 없고, 이메일에 해당하는 회원이 없으면, 회원 가입 및 소셜 연동 생성 후 로그인 처리한다.")
    fun test3() {
        // given
        val code = "code"
        val socialServiceName = "google"
        val socialServiceUserId = "google-user-id"
        val email = "hello@gmail.com"

        val oAuth2AuthorizationRequest = googleOAuth2AuthorizationRequestFixture()
        oAuth2AuthorizationRequestPersistencePortFixture.save(oAuth2AuthorizationRequest, expiresAt = appDateTimeFixture(minute = 16))


        val command = SocialLoginCommand(
            state = oAuth2AuthorizationRequest.state,
            code = code,
            currentTime = appDateTimeFixture(minute = 13)
        )

        oAuth2AccessTokenClientPortFixture.changeAccessToken("accessToken")
        oAuth2AccessTokenClientPortFixture.changeIdToken("IdToken")

        oidcOAuth2UserPrincipalPortFixture.changeSocialServiceName(socialServiceName)
        oidcOAuth2UserPrincipalPortFixture.changeSocialServiceUserId(socialServiceUserId)
        oidcOAuth2UserPrincipalPortFixture.changeEmail(email)

        // when
        val (createdUser, accessToken, refreshToken) = processor.socialLogin(command)

        // then
        val findSocialConnection = socialConnectionPersistencePortFixture.read(
            socialService = SocialService.GOOGLE,
            socialServiceUserId = socialServiceUserId
        )!!
        val findUser = userPersistencePortFixture.findByIdOrNull(findSocialConnection.userId)!!
        val userTokenIds = userRefreshTokenIdListPersistencePortFixture.findAll(createdUser!!.userId)
        val findOAuth2AuthorizationRequest = oAuth2AuthorizationRequestPersistencePortFixture.read(oAuth2AuthorizationRequest.state)

        assertThat(createdUser).isNotNull
        assertThat(createdUser.userId).isNotNull()
        assertThat(createdUser.email).isEqualTo(email)
        assertThat(createdUser.username).isNotNull()
        assertThat(createdUser.nickname).isNotNull()
        assertThat(createdUser.password).isNotNull()
        assertThat(createdUser.registeredAt).isEqualTo(command.currentTime)
        assertThat(createdUser.role).isEqualTo(Role.USER)
        assertThat(findUser.userId).isEqualTo(createdUser.userId)
        assertThat(findUser.email).isEqualTo(createdUser.email)
        assertThat(findUser.username).isEqualTo(createdUser.username)
        assertThat(findUser.nickname).isEqualTo(createdUser.nickname)
        assertThat(findUser.password).isEqualTo(createdUser.password)
        assertThat(findUser.role).isEqualTo(createdUser.role)
        assertThat(findUser.registeredAt).isEqualTo(createdUser.registeredAt)
        assertThat(accessToken.authUser.userId).isEqualTo(createdUser.userId)
        assertThat(accessToken.authUser.role).isEqualTo(createdUser.role)
        assertThat(accessToken.tokenValue).isNotNull()
        assertThat(accessToken.issuedAt).isEqualTo(command.currentTime)
        assertThat(accessToken.expiresAt).isEqualTo(command.currentTime.plusMinutes(30))
        assertThat(refreshToken.userId).isEqualTo(createdUser.userId)
        assertThat(refreshToken.refreshTokenId).isNotNull()
        assertThat(refreshToken.tokenValue).isNotNull()
        assertThat(refreshToken.issuedAt).isEqualTo(command.currentTime)
        assertThat(refreshToken.expiresAt).isEqualTo(command.currentTime.plusHours(RefreshTokenHandler.REFRESH_TOKEN_VALIDITY_HOUR))
        assertThat(userTokenIds).containsExactly(refreshToken.refreshTokenId)
        assertThat(refreshTokenIdPersistencePortFixture.exists(createdUser.userId, refreshToken.refreshTokenId)).isTrue()
        assertThat(findSocialConnection.socialConnectionId).isNotNull
        assertThat(findSocialConnection.userId).isNotNull
        assertThat(findSocialConnection.socialService).isEqualTo(SocialService.GOOGLE)
        assertThat(findSocialConnection.socialServiceUserId).isEqualTo(socialServiceUserId)
        assertThat(findSocialConnection.linkedAt).isEqualTo(command.currentTime)
        assertThat(findOAuth2AuthorizationRequest).isNull()
    }


    @Test
    @DisplayName("state 에 해당하는 OAuth2AuthorizationRequest 가 없으면 예외 발생")
    fun test4() {
        // given
        val command = SocialLoginCommand(
            state = "adfadfadf",
            code = "adagdgaagggh",
            currentTime = appDateTimeFixture(minute = 13)
        )

        // when
        val ex = assertThrows<OAuth2AuthorizationRequestNotFoundException> {
            processor.socialLogin(command)
        }

        // then
        assertThat(ex.args).containsExactly(command.state)
    }
}
