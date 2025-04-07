package com.ttasjwi.board.system.domain.member.event

import com.ttasjwi.board.system.domain.member.model.EmailVerification
import com.ttasjwi.board.system.global.domain.DomainEvent
import com.ttasjwi.board.system.global.time.AppDateTime

class EmailVerifiedEvent(
    email: String,
    verifiedAt: AppDateTime,
    verificationExpiresAt: AppDateTime,
) : DomainEvent<EmailVerifiedEvent.Data>(
    occurredAt = verifiedAt,
    data = Data(
        email,
        verifiedAt,
        verificationExpiresAt
    )
) {

    class Data(
        val email: String,
        val verifiedAt: AppDateTime,
        val verificationExpiresAt: AppDateTime,
    )

    companion object {

        internal fun create(emailVerification: EmailVerification): EmailVerifiedEvent {
            return EmailVerifiedEvent(
                email = emailVerification.email,
                verifiedAt = emailVerification.verifiedAt!!,
                verificationExpiresAt = emailVerification.verificationExpiresAt!!
            )
        }
    }
}
