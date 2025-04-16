package com.ttasjwi.board.system.user.domain

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.user.domain.model.fixture.emailVerificationFixtureVerified
import com.ttasjwi.board.system.user.domain.port.fixture.EmailVerificationPersistencePortFixture
import com.ttasjwi.board.system.user.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("RegisterMemberUseCaseImpl: 회원가입 유즈케이스 구현체")
class RegisterMemberUseCaseImplTest {

    private lateinit var useCase: RegisterMemberUseCase
    private lateinit var emailVerificationPersistencePortFixture: EmailVerificationPersistencePortFixture
    private lateinit var currentTime: AppDateTime

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        currentTime = appDateTimeFixture(minute = 6)
        container.timeManagerFixture.changeCurrentTime(currentTime)
        emailVerificationPersistencePortFixture = container.emailVerificationPersistencePortFixture
        useCase = container.registerMemberUseCase
    }

    @Test
    @DisplayName("회원가입을 처리하고 그 결과를 반환한다.")
    fun test() {
        val email = "hello@gmail.com"
        emailVerificationPersistencePortFixture.save(
            emailVerificationFixtureVerified(
                email = email,
                code = "code",
                codeCreatedAt = appDateTimeFixture(minute = 0),
                codeExpiresAt = appDateTimeFixture(minute = 5),
                verifiedAt = appDateTimeFixture(minute = 3),
                verificationExpiresAt = appDateTimeFixture(minute = 33),
            ), appDateTimeFixture(minute = 33)
        )

        val request = RegisterMemberRequest(
            email = email,
            password = "1111",
            username = "testuser",
            nickname = "testnick",
        )

        val response = useCase.register(request)

        assertThat(response.memberId).isNotNull()
        assertThat(response.email).isEqualTo(request.email)
        assertThat(response.username).isEqualTo(request.username)
        assertThat(response.nickname).isEqualTo(request.nickname)
        assertThat(response.role).isNotNull()
        assertThat(response.registeredAt).isEqualTo(currentTime.toZonedDateTime())
    }
}
