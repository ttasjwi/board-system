package com.ttasjwi.board.system.member.domain.model

import com.ttasjwi.board.system.core.time.fixture.timeFixture
import com.ttasjwi.board.system.member.domain.model.fixture.emailFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("EmailVerification 테스트")
class EmailVerificationTest {

    @Nested
    @DisplayName("restore: 값으로부터 이메일 인증 인스턴스를 복원한다.")
    inner class Restore {

        @Test
        @DisplayName("전달된 값으로 인스턴스를 성공적으로 복원해야한다.")
        fun test() {
            val email = "nyaru@gmail.com"
            val code = "ads7fa778"
            val codeCreatedAt = timeFixture(minute = 3)
            val codeExpiresAt = timeFixture(minute = 8)
            val verifiedAt = timeFixture(minute = 5)
            val verificationExpiresAt = timeFixture(minute = 35)

            val emailVerification = EmailVerification.restore(
                email = email,
                code = code,
                codeCreatedAt = codeCreatedAt,
                codeExpiresAt = codeExpiresAt,
                verifiedAt = verifiedAt,
                verificationExpiresAt = verificationExpiresAt
            )

            assertThat(emailVerification.email).isEqualTo(emailFixture(email))
            assertThat(emailVerification.code).isEqualTo(code)
            assertThat(emailVerification.codeCreatedAt).isEqualTo(codeCreatedAt)
            assertThat(emailVerification.codeExpiresAt).isEqualTo(codeExpiresAt)
            assertThat(emailVerification.verifiedAt).isEqualTo(verifiedAt)
            assertThat(emailVerification.verificationExpiresAt).isEqualTo(verificationExpiresAt)
        }

    }
}
