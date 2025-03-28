package com.ttasjwi.board.system.member.domain.service.impl

import com.ttasjwi.board.system.common.time.fixture.timeFixture
import com.ttasjwi.board.system.member.domain.model.EmailVerification
import com.ttasjwi.board.system.member.domain.model.fixture.emailFixture
import com.ttasjwi.board.system.member.domain.service.EmailVerificationCreator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("EmailVerificationCreatorImpl 테스트")
class EmailVerificationCreatorImplTest {

    private lateinit var emailVerificationCreator: EmailVerificationCreator

    @BeforeEach
    fun setup() {
        emailVerificationCreator = EmailverificationCreatorImpl()
    }

    @Test
    @DisplayName("Create: 이메일 인증을 생성한다")
    fun testCode() {
        val email = emailFixture("soso@gmail.com")
        val currentTime = timeFixture(minute = 0)

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
