package com.ttasjwi.board.system.domain.member.service

import com.ttasjwi.board.system.domain.member.model.emailVerificationFixtureNotVerified
import com.ttasjwi.board.system.domain.member.model.emailVerificationFixtureVerified
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.*

@DisplayName("EmailVerificationEventCreator 픽스쳐 테스트")
class EmailVerificationEventCreatorFixtureTest {

    private lateinit var emailVerificationEventCreatorFixture: EmailVerificationEventCreatorFixture

    @BeforeEach
    fun setup() {
        emailVerificationEventCreatorFixture = EmailVerificationEventCreatorFixture()
    }

    @Test
    @DisplayName("OnVerificationStarted: 이메일 인증 시작됨 이벤트를 생성한다")
    fun testOnVerificationStarted() {
        val emailVerification = emailVerificationFixtureNotVerified()
        val locale = Locale.ENGLISH

        val event = emailVerificationEventCreatorFixture.onVerificationStarted(
            emailVerification = emailVerification,
            locale = locale
        )
        val data = event.data

        assertThat(event.occurredAt).isEqualTo(emailVerification.codeCreatedAt)
        assertThat(data.email).isEqualTo(emailVerification.email)
        assertThat(data.code).isEqualTo(emailVerification.code)
        assertThat(data.codeCreatedAt).isEqualTo(emailVerification.codeCreatedAt)
        assertThat(data.codeExpiresAt).isEqualTo(emailVerification.codeExpiresAt)
        assertThat(data.locale).isEqualTo(locale)
    }

    @Test
    @DisplayName("OnVerified: 이메일 인증됨 이벤트를 생성한다")
    fun testOnVerified() {
        val emailVerification = emailVerificationFixtureVerified()

        val event = emailVerificationEventCreatorFixture.onVerified(emailVerification)
        val data = event.data

        assertThat(event.occurredAt).isEqualTo(emailVerification.verifiedAt)
        assertThat(data.email).isEqualTo(emailVerification.email)
        assertThat(data.verifiedAt).isEqualTo(emailVerification.verifiedAt)
        assertThat(data.verificationExpiresAt).isEqualTo(emailVerification.verificationExpiresAt)
    }
}
