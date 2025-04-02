package com.ttasjwi.board.system.member.domain.service.impl

import com.ttasjwi.board.system.member.domain.model.fixture.emailVerificationFixtureNotVerified
import com.ttasjwi.board.system.member.domain.model.fixture.emailVerificationFixtureVerified
import com.ttasjwi.board.system.member.domain.service.EmailVerificationEventCreator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.*

@DisplayName("EmailVerificationEventCreatorImpl 테스트")
class EmailVerificationEventCreatorImplTest {

    private lateinit var emailVerificationEventCreator: EmailVerificationEventCreator

    @BeforeEach
    fun setup() {
        emailVerificationEventCreator = EmailVerificationEventCreatorImpl()
    }

    @Test
    @DisplayName("onVerificationStarted = 이메일인증 시작됨 이벤트를 생성한다")
    fun testOnVerificationStarted() {
        val emailVerification = emailVerificationFixtureNotVerified()
        val locale = Locale.ENGLISH

        val event = emailVerificationEventCreator.onVerificationStarted(emailVerification, locale)
        val data = event.data

        assertThat(event.occurredAt).isEqualTo(emailVerification.codeCreatedAt)
        assertThat(data.email).isEqualTo(emailVerification.email)
        assertThat(data.code).isEqualTo(emailVerification.code)
        assertThat(data.codeCreatedAt).isEqualTo(emailVerification.codeCreatedAt)
        assertThat(data.codeExpiresAt).isEqualTo(emailVerification.codeExpiresAt)
    }

    @Test
    @DisplayName("onVerified: 이메일 인증됨 이벤트를 생성한다")
    fun testOnVerified() {
        val emailVerification = emailVerificationFixtureVerified()

        val event = emailVerificationEventCreator.onVerified(emailVerification)
        val data = event.data

        assertThat(event.occurredAt).isEqualTo(emailVerification.verifiedAt)
        assertThat(data.email).isEqualTo(emailVerification.email)
        assertThat(data.verifiedAt).isEqualTo(emailVerification.verifiedAt)
        assertThat(data.verificationExpiresAt).isEqualTo(emailVerification.verificationExpiresAt)
    }
}
