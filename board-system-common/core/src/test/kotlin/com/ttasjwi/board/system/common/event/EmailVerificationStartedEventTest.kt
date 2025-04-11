package com.ttasjwi.board.system.common.event

import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.Locale

class EmailVerificationStartedEventTest {


    @Test
    @DisplayName("")
    fun createTest() {
        val email = "hello@gmail.com"
        val code = "1234a"
        val codeCreatedAt = appDateTimeFixture()
        val codeExpiresAt = appDateTimeFixture(minute = 5)
        val locale = Locale.KOREAN

        // when
        val event = EmailVerificationStartedEvent.create(
            email = email,
            code = code,
            codeCreatedAt = codeCreatedAt,
            codeExpiresAt = codeExpiresAt,
            locale = locale,
            eventCreatedAt = codeCreatedAt
        )

        // then
        assertThat(event.eventType).isEqualTo(EventType.EMAIL_VERIFICATION_STARTED)
        assertThat(event.createdAt).isEqualTo(codeCreatedAt.toZonedDateTime())
        assertThat(event.payload.email).isEqualTo(email)
        assertThat(event.payload.code).isEqualTo(code)
        assertThat(event.payload.codeCreatedAt).isEqualTo(codeCreatedAt.toZonedDateTime())
        assertThat(event.payload.codeExpiresAt).isEqualTo(codeExpiresAt.toZonedDateTime())
        assertThat(event.payload.locale).isEqualTo(locale)
    }
}
