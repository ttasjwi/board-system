package com.ttasjwi.board.system.user.domain

import com.ttasjwi.board.system.common.time.fixture.TimeManagerFixture
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.user.domain.model.SocialService
import com.ttasjwi.board.system.user.domain.model.fixture.userFixture
import com.ttasjwi.board.system.user.domain.model.fixture.socialConnectionFixture
import com.ttasjwi.board.system.user.domain.port.fixture.UserPersistencePortFixture
import com.ttasjwi.board.system.user.domain.port.fixture.SocialConnectionPersistencePortFixture
import com.ttasjwi.board.system.user.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("SocialLoginUseCaseImpl: 소셜로그인 요청을 받아 처리하는 유즈케이스 구현체")
class SocialLoginUseCaseImplTest {

    private lateinit var useCase: SocialLoginUseCase
    private lateinit var timeManagerFixture: TimeManagerFixture
    private lateinit var userPersistencePortFixture: UserPersistencePortFixture
    private lateinit var socialConnectionPersistencePortFixture: SocialConnectionPersistencePortFixture

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        timeManagerFixture = container.timeManagerFixture
        userPersistencePortFixture = container.userPersistencePortFixture
        socialConnectionPersistencePortFixture = container.socialConnectionPersistencePortFixture
        useCase = container.socialLoginUseCase
    }

    @Test
    @DisplayName("기존 회원인 경우, 액세스토큰/리프레시토큰 정보가 포함된다.")
    fun test() {
        // given
        val socialServiceName = "google"
        val socialServiceUserId = "abcd12345"
        val email = "hello@gmail.com"
        val currentTime = appDateTimeFixture(minute = 5)
        timeManagerFixture.changeCurrentTime(currentTime)

        val user = userPersistencePortFixture.save(userFixture(email = email))
        socialConnectionPersistencePortFixture.save(
            socialConnectionFixture(
                userId = user.userId,
                socialService = SocialService.GOOGLE,
                socialServiceUserId = socialServiceUserId,
                linkedAt = appDateTimeFixture(minute = 3)
            )
        )

        val request = SocialLoginRequest(
            socialServiceName = socialServiceName,
            socialServiceUserId = socialServiceUserId,
            email = email,
        )

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

        val request = SocialLoginRequest(
            socialServiceName = socialServiceName,
            socialServiceUserId = socialServiceUserId,
            email = email,
        )

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
        assertThat(createdUser.email).isEqualTo(request.email)
        assertThat(createdUser.userId).isNotNull()
        assertThat(createdUser.username).isNotNull()
        assertThat(createdUser.nickname).isNotNull()
        assertThat(createdUser.role).isNotNull()
        assertThat(createdUser.registeredAt).isEqualTo(currentTime.toZonedDateTime())
    }
}
