package com.ttasjwi.board.system.domain.member.service.impl

import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.domain.member.model.EmailVerification
import com.ttasjwi.board.system.domain.member.service.EmailVerificationCreator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("EmailVerificationCreatorImpl 테스트")
class EmailVerificationCreatorImplTest {

    private lateinit var emailVerificationCreator: EmailVerificationCreator

    @BeforeEach
    fun setup() {
        emailVerificationCreator = EmailVerificationCreatorImpl()
    }

    @Test
    @DisplayName("Create: 이메일 인증을 생성한다")
    fun testCode() {
        val email = "soso@gmail.com"
        val currentTime = appDateTimeFixture(minute = 0)

        val emailVerification = emailVerificationCreator.create(
            email = email,
            currentTime = currentTime
        )

        assertThat(emailVerification.email).isEqualTo(email)
        assertThat(emailVerification.code.length).isEqualTo(EmailVerification.CODE_LENGTH)
        assertThat(emailVerification.codeCreatedAt).isEqualTo(currentTime)
        assertThat(emailVerification.codeExpiresAt).isEqualTo(currentTime.plusMinutes(EmailVerification.CODE_VALIDITY_MINUTE))
        assertThat(emailVerification.verifiedAt).isNull()
        assertThat(emailVerification.verificationExpiresAt).isNull()
    }
}
