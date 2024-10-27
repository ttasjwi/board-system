package com.ttasjwi.board.system.member.domain.event

import com.ttasjwi.board.system.core.domain.event.DomainEvent
import java.time.ZonedDateTime

class EmailVerificationStartedEvent(
    email: String,
    code: String,
    codeCreatedAt: ZonedDateTime,
    codeExpiresAt: ZonedDateTime,
) : DomainEvent<EmailVerificationStartedEvent.Data>(
    occurredAt = codeCreatedAt,
    data = Data(email, code, codeCreatedAt, codeExpiresAt)
) {

    class Data(
        val email: String,
        val code: String,
        val codeCreatedAt: ZonedDateTime,
        val codeExpiresAt: ZonedDateTime,
    )
}
