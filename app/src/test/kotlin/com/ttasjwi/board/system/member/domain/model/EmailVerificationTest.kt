package com.ttasjwi.board.system.member.domain.model

import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
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
            val codeCreatedAt = appDateTimeFixture(minute = 3).toInstant()
            val codeExpiresAt = appDateTimeFixture(minute = 8).toInstant()
            val verifiedAt = appDateTimeFixture(minute = 5).toInstant()
            val verificationExpiresAt = appDateTimeFixture(minute = 35).toInstant()

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
            assertThat(emailVerification.codeCreatedAt.toInstant()).isEqualTo(codeCreatedAt)
            assertThat(emailVerification.codeExpiresAt.toInstant()).isEqualTo(codeExpiresAt)
            assertThat(emailVerification.verifiedAt!!.toInstant()).isEqualTo(verifiedAt)
            assertThat(emailVerification.verificationExpiresAt!!.toInstant()).isEqualTo(verificationExpiresAt)
        }

    }
}
