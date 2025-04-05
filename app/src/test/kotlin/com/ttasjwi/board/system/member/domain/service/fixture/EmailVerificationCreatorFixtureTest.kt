package com.ttasjwi.board.system.member.domain.service.fixture

import com.ttasjwi.board.system.global.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("EmailVerificationCreator 픽스쳐 테스트")
class EmailVerificationCreatorFixtureTest {

    private lateinit var emailVerificationCreatorFixture: EmailVerificationCreatorFixture

    @BeforeEach
    fun setup() {
        emailVerificationCreatorFixture = EmailVerificationCreatorFixture()
    }

    @Test
    @DisplayName("Create: 이메일 인증을 생성한다")
    fun testCode() {
        val email = "soso@gmail.com"
        val currentTime = appDateTimeFixture(minute = 0)

        val emailVerification = emailVerificationCreatorFixture.create(
            email = email,
            currentTime = currentTime
        )

        assertThat(emailVerification.email).isEqualTo(email)
        assertThat(emailVerification.code).isEqualTo("code")
        assertThat(emailVerification.codeCreatedAt).isEqualTo(currentTime)
        assertThat(emailVerification.codeExpiresAt).isEqualTo(appDateTimeFixture(minute = 5))
        assertThat(emailVerification.verifiedAt).isNull()
        assertThat(emailVerification.verificationExpiresAt).isNull()
    }
}
