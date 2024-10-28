package com.ttasjwi.board.system.member.domain.event

import com.ttasjwi.board.system.core.domain.event.DomainEvent
import java.time.ZonedDateTime

class EmailVerifiedEvent(
    email: String,
    verifiedAt: ZonedDateTime,
    verificationExpiresAt: ZonedDateTime,
): DomainEvent<EmailVerifiedEvent.Data>(
    occurredAt = verifiedAt,
    data = Data(email, verifiedAt, verificationExpiresAt)
) {

    class Data (
        val email: String,
        val verifiedAt: ZonedDateTime,
        val verificationExpiresAt: ZonedDateTime,
    )
}
