package com.ttasjwi.board.system.member.domain.event

import com.ttasjwi.board.system.core.domain.event.DomainEvent
import java.time.ZonedDateTime
import java.util.*

class EmailVerificationStartedEvent
internal constructor(
    email: String,
    code: String,
    codeCreatedAt: ZonedDateTime,
    codeExpiresAt: ZonedDateTime,
    locale: Locale,
) : DomainEvent<EmailVerificationStartedEvent.Data>(
    occurredAt = codeCreatedAt,
    data = Data(email, code, codeCreatedAt, codeExpiresAt, locale)
) {

    class Data(
        val email: String,
        val code: String,
        val codeCreatedAt: ZonedDateTime,
        val codeExpiresAt: ZonedDateTime,
        val locale: Locale
    )
}
