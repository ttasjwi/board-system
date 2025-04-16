package com.ttasjwi.board.system.domain.fixture

import com.ttasjwi.board.system.user.domain.EmailVerificationRequest
import com.ttasjwi.board.system.user.domain.fixture.EmailVerificationUseCaseFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("EmailVerificationUseCaseFixture 테스트")
class EmailVerificationUseCaseFixtureTest {

    private lateinit var emailVerificationUseCaseFixture: EmailVerificationUseCaseFixture

    @BeforeEach
    fun setup() {
        emailVerificationUseCaseFixture = EmailVerificationUseCaseFixture()
    }

    @Test
    @DisplayName("이메일 인증 결과를 내려준다.")
    fun test() {
        val request = EmailVerificationRequest(email = "test@test.com", code = "1111")

        val result = emailVerificationUseCaseFixture.emailVerification(request)

        assertThat(result.email).isEqualTo(request.email)
        assertThat(result.verificationExpiresAt).isNotNull()
    }
}
