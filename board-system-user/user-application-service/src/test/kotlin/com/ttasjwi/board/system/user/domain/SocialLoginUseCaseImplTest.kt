package com.ttasjwi.board.system.user.domain

import com.ttasjwi.board.system.common.time.fixture.TimeManagerFixture
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.user.domain.model.SocialService
import com.ttasjwi.board.system.user.domain.model.fixture.kakaoOAuth2AuthorizationRequestFixture
import com.ttasjwi.board.system.user.domain.model.fixture.socialConnectionFixture
import com.ttasjwi.board.system.user.domain.model.fixture.userFixture
import com.ttasjwi.board.system.user.domain.port.fixture.*
import com.ttasjwi.board.system.user.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[user-application-service] SocialLoginUseCaseImpl 테스트")
class SocialLoginUseCaseImplTest {

    private lateinit var useCase: SocialLoginUseCase
    private lateinit var timeManagerFixture: TimeManagerFixture
    private lateinit var oAuth2AuthorizationRequestPersistencePortFixture: OAuth2AuthorizationRequestPersistencePortFixture
    private lateinit var socialConnectionPersistencePortFixture: SocialConnectionPersistencePortFixture
    private lateinit var userPersistencePortFixture: UserPersistencePortFixture
    private lateinit var oAuth2AccessTokenClientPortFixture: OAuth2AccessTokenClientPortFixture
    private lateinit var oidcOAuth2UserPrincipalPortFixture: OidcOAuth2UserPrincipalPortFixture

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        useCase = container.socialLoginUseCase
        oAuth2AuthorizationRequestPersistencePortFixture = container.oAuth2AuthorizationRequestPersistencePortFixture
        socialConnectionPersistencePortFixture = container.socialConnectionPersistencePortFixture
        userPersistencePortFixture = container.userPersistencePortFixture
        timeManagerFixture = container.timeManagerFixture
        oAuth2AccessTokenClientPortFixture = container.oAuth2AccessTokenClientPortFixture
        oidcOAuth2UserPrincipalPortFixture = container.oidcOAuth2UserPrincipalPortFixture
    }


    @Test
    @DisplayName("기존 회원인 경우, 액세스토큰/리프레시토큰 정보가 포함된다.")
    fun test() {
        // given
        val socialServiceName = "kakao"
        val socialServiceUserId = "abcd12345"
        val email = "hello@kakao.com"
        val currentTime = appDateTimeFixture(minute = 5)
        timeManagerFixture.changeCurrentTime(currentTime)

        val user = userPersistencePortFixture.save(userFixture(email = email))
        socialConnectionPersistencePortFixture.save(
            socialConnectionFixture(
                userId = user.userId,
                socialService = SocialService.KAKAO,
                socialServiceUserId = socialServiceUserId,
                linkedAt = appDateTimeFixture(minute = 3)
            )
        )

        val oAuth2AuthorizationRequest = kakaoOAuth2AuthorizationRequestFixture()
        oAuth2AuthorizationRequestPersistencePortFixture.save(
            oAuth2AuthorizationRequest,
            expiresAt = currentTime.plusMinutes(3)
        )

        val request = SocialLoginRequest(
            state = oAuth2AuthorizationRequest.state,
            code = "code"
        )

        oAuth2AccessTokenClientPortFixture.changeAccessToken("accessToken")
        oAuth2AccessTokenClientPortFixture.changeIdToken("IdToken")

        oidcOAuth2UserPrincipalPortFixture.changeSocialServiceName(socialServiceName)
        oidcOAuth2UserPrincipalPortFixture.changeSocialServiceUserId(socialServiceUserId)
        oidcOAuth2UserPrincipalPortFixture.changeEmail(email)

        // when
        val result = useCase.socialLogin(request)

        // then
        assertThat(result.accessToken).isNotNull()
        assertThat(result.accessTokenType).isEqualTo("Bearer")
        assertThat(result.accessTokenExpiresAt).isNotNull()
        assertThat(result.refreshToken).isNotNull()
        assertThat(result.refreshTokenExpiresAt).isNotNull()
        assertThat(result.userCreated).isFalse()
        assertThat(result.createdUser).isNull()
    }

    @Test
    @DisplayName("기존 회원이 아닌 신규회원인 경우, 생성된 회원정보도 응답에 포함된다.")
    fun test2() {
        // given
        val socialServiceName = "google"
        val socialServiceUserId = "abcd12345"
        val email = "hello@gmail.com"
        val currentTime = appDateTimeFixture(minute = 5)
        timeManagerFixture.changeCurrentTime(currentTime)

        val oAuth2AuthorizationRequest = kakaoOAuth2AuthorizationRequestFixture()
        oAuth2AuthorizationRequestPersistencePortFixture.save(
            oAuth2AuthorizationRequest,
            expiresAt = currentTime.plusMinutes(3)
        )

        val request = SocialLoginRequest(
            state = oAuth2AuthorizationRequest.state,
            code = "code"
        )

        oAuth2AccessTokenClientPortFixture.changeAccessToken("accessToken")
        oAuth2AccessTokenClientPortFixture.changeIdToken("IdToken")

        oidcOAuth2UserPrincipalPortFixture.changeSocialServiceName(socialServiceName)
        oidcOAuth2UserPrincipalPortFixture.changeSocialServiceUserId(socialServiceUserId)
        oidcOAuth2UserPrincipalPortFixture.changeEmail(email)

        // when
        val response = useCase.socialLogin(request)
        val createdUser = response.createdUser!!

        // then
        assertThat(response.accessToken).isNotNull()
        assertThat(response.accessTokenType).isEqualTo("Bearer")
        assertThat(response.accessTokenExpiresAt).isNotNull()
        assertThat(response.refreshToken).isNotNull()
        assertThat(response.refreshTokenExpiresAt).isNotNull()
        assertThat(response.userCreated).isTrue()
        assertThat(createdUser).isNotNull
        assertThat(createdUser.email).isEqualTo(email)
        assertThat(createdUser.userId).isNotNull()
        assertThat(createdUser.username).isNotNull()
        assertThat(createdUser.nickname).isNotNull()
        assertThat(createdUser.role).isNotNull()
        assertThat(createdUser.registeredAt).isEqualTo(currentTime.toZonedDateTime())
    }

}
