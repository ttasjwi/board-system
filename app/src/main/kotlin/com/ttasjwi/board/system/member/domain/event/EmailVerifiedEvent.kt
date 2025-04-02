package com.ttasjwi.board.system.member.domain.event

import com.ttasjwi.board.system.common.domain.event.DomainEvent
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.member.domain.model.EmailVerification

class EmailVerifiedEvent(
    email: String,
    verifiedAt: AppDateTime,
    verificationExpiresAt: AppDateTime,
) : DomainEvent<EmailVerifiedEvent.Data>(
    occurredAt = verifiedAt,
    data = Data(email, verifiedAt, verificationExpiresAt)
) {

    class Data(
        val email: String,
        val verifiedAt: AppDateTime,
        val verificationExpiresAt: AppDateTime,
    )

    companion object {

        internal fun create(emailVerification: EmailVerification): EmailVerifiedEvent {
            return EmailVerifiedEvent(
                email = emailVerification.email.value,
                verifiedAt = emailVerification.verifiedAt!!,
                verificationExpiresAt = emailVerification.verificationExpiresAt!!
            )
        }
    }
}
