package com.ttasjwi.board.system.user.domain.processor

import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.user.domain.dto.SocialLoginCommand
import com.ttasjwi.board.system.user.domain.model.SocialService
import com.ttasjwi.board.system.user.domain.model.fixture.userFixture
import com.ttasjwi.board.system.user.domain.model.fixture.socialConnectionFixture
import com.ttasjwi.board.system.user.domain.model.fixture.socialServiceUserFixture
import com.ttasjwi.board.system.user.domain.port.fixture.UserPersistencePortFixture
import com.ttasjwi.board.system.user.domain.port.fixture.UserRefreshTokenIdListPersistencePortFixture
import com.ttasjwi.board.system.user.domain.port.fixture.RefreshTokenIdPersistencePortFixture
import com.ttasjwi.board.system.user.domain.port.fixture.SocialConnectionPersistencePortFixture
import com.ttasjwi.board.system.user.domain.service.RefreshTokenHandler
import com.ttasjwi.board.system.user.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("SocialLoginProcessor: 소셜 로그인 애플리케이션 명령을 실질적으로 처리한다")
class SocialLoginProcessorTest {

    private lateinit var socialLoginProcessor: SocialLoginProcessor
    private lateinit var userPersistencePortFixture: UserPersistencePortFixture
    private lateinit var socialConnectionPersistencePortFixture: SocialConnectionPersistencePortFixture
    private lateinit var userRefreshTokenIdListPersistencePortFixture: UserRefreshTokenIdListPersistencePortFixture
    private lateinit var refreshTokenIdPersistencePortFixture: RefreshTokenIdPersistencePortFixture

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        userPersistencePortFixture = container.userPersistencePortFixture
        socialConnectionPersistencePortFixture = container.socialConnectionPersistencePortFixture
        socialLoginProcessor = container.socialLoginProcessor
        userRefreshTokenIdListPersistencePortFixture = container.userRefreshTokenIdListPersistencePortFixture
        refreshTokenIdPersistencePortFixture = container.refreshTokenIdPersistencePortFixture
    }


    @Test
    @DisplayName("소셜 연동에 해당하는 회원이 있으면, 로그인 처리한다.")
    fun test1() {
        // given
        val socialService = SocialService.GOOGLE
        val socialServiceUserId = "abcd12345"
        val email = "hello@gmail.com"
        val user = userPersistencePortFixture.save(userFixture(email = email))
        socialConnectionPersistencePortFixture.save(
            socialConnectionFixture(
                socialConnectionId = 15567L,
                userId = user.userId,
                socialService = socialService,
                socialServiceUserId = socialServiceUserId,
                linkedAt = appDateTimeFixture(minute = 3)
            )
        )

        val command = SocialLoginCommand(
            socialServiceUser = socialServiceUserFixture(socialService, socialServiceUserId),
            email = email,
            currentTime = appDateTimeFixture(minute = 5),
        )

        // when
        val (createdUser, accessToken, refreshToken) = socialLoginProcessor.socialLogin(command)

        // then
        val userTokenIds = userRefreshTokenIdListPersistencePortFixture.findAll(user.userId)
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
    }

    @Test
    @DisplayName("소셜 연동은 없지만 이메일에 해당하는 회원이 있으면, 소셜 연동 생성 후 로그인 처리한다.")
    fun test2() {
        // given
        val socialService = SocialService.GOOGLE
        val socialServiceUserId = "abcd12345"
        val email = "hello@gmail.com"
        val user = userPersistencePortFixture.save(userFixture(email = email))

        val command = SocialLoginCommand(
            socialServiceUser = socialServiceUserFixture(socialService, socialServiceUserId),
            email = email,
            currentTime = appDateTimeFixture(minute = 5),
        )

        // when
        val (createdUser, accessToken, refreshToken) = socialLoginProcessor.socialLogin(command)

        // then
        val userTokenIds = userRefreshTokenIdListPersistencePortFixture.findAll(user.userId)
        val findSocialConnection = socialConnectionPersistencePortFixture.findBySocialServiceUserOrNull(
            socialServiceUser = command.socialServiceUser
        )!!

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
        assertThat(findSocialConnection.socialServiceUser).isEqualTo(command.socialServiceUser)
        assertThat(findSocialConnection.linkedAt).isEqualTo(command.currentTime)
    }

    @Test
    @DisplayName("소셜 연동도 없고, 이메일에 해당하는 회원이 없으면, 회원 가입 및 소셜 연동 생성 후 로그인 처리한다.")
    fun test3() {
        // given
        val socialService = SocialService.GOOGLE
        val socialServiceUserId = "abcd12345"
        val email = "hello@gmail.com"

        val command = SocialLoginCommand(
            socialServiceUser = socialServiceUserFixture(socialService, socialServiceUserId),
            email = email,
            currentTime = appDateTimeFixture(minute = 5),
        )

        // when
        val (createdUser, accessToken, refreshToken) = socialLoginProcessor.socialLogin(command)

        // then
        val findSocialConnection = socialConnectionPersistencePortFixture.findBySocialServiceUserOrNull(
            socialServiceUser = command.socialServiceUser
        )!!
        val findUser = userPersistencePortFixture.findByIdOrNull(findSocialConnection.userId)!!
        val userTokenIds = userRefreshTokenIdListPersistencePortFixture.findAll(createdUser!!.userId)

        assertThat(createdUser).isNotNull
        assertThat(createdUser.userId).isNotNull()
        assertThat(createdUser.email).isEqualTo(command.email)
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
        assertThat(
            refreshTokenIdPersistencePortFixture.exists(
                createdUser.userId,
                refreshToken.refreshTokenId
            )
        ).isTrue()
        assertThat(findSocialConnection.socialConnectionId).isNotNull
        assertThat(findSocialConnection.userId).isNotNull
        assertThat(findSocialConnection.socialServiceUser).isEqualTo(command.socialServiceUser)
        assertThat(findSocialConnection.linkedAt).isEqualTo(command.currentTime)
    }
}
