package com.ttasjwi.board.system.common.event

import com.ttasjwi.board.system.common.time.AppDateTime
import java.time.ZonedDateTime
import java.util.*

class EmailVerificationStartedEvent
internal constructor(
    email: String,
    code: String,
    codeCreatedAt: ZonedDateTime,
    codeExpiresAt: ZonedDateTime,
    locale: Locale,
    eventCreatedAt: ZonedDateTime,
) : Event<EmailVerificationStartedEvent.EmailVerificationStartedEventPayload>(
    eventType = EventType.EMAIL_VERIFICATION_STARTED,
    createdAt = eventCreatedAt,
    payload = EmailVerificationStartedEventPayload(
        email = email,
        code = code,
        codeCreatedAt = codeCreatedAt,
        codeExpiresAt = codeExpiresAt,
        locale = locale
    ),
) {

    companion object {

        fun create(
            email: String,
            code: String,
            codeCreatedAt: AppDateTime,
            codeExpiresAt: AppDateTime,
            locale: Locale,
            eventCreatedAt: AppDateTime
        ): EmailVerificationStartedEvent {
            return EmailVerificationStartedEvent(
                email = email,
                code = code,
                codeCreatedAt = codeCreatedAt.toZonedDateTime(),
                codeExpiresAt = codeExpiresAt.toZonedDateTime(),
                locale = locale,
                eventCreatedAt = eventCreatedAt.toZonedDateTime()
            )
        }
    }

    class EmailVerificationStartedEventPayload(
        val email: String,
        val code: String,
        val codeCreatedAt: ZonedDateTime,
        val codeExpiresAt: ZonedDateTime,
        val locale: Locale,
    ) : EventPayload
}
