package com.ttasjwi.board.system.domain.member.event

import com.ttasjwi.board.system.domain.member.model.EmailVerification
import com.ttasjwi.board.system.global.domain.DomainEvent
import com.ttasjwi.board.system.global.time.AppDateTime
import java.util.*

class EmailVerificationStartedEvent
internal constructor(
    email: String,
    code: String,
    codeCreatedAt: AppDateTime,
    codeExpiresAt: AppDateTime,
    locale: Locale,
) : DomainEvent<EmailVerificationStartedEvent.Data>(
    occurredAt = codeCreatedAt,
    data = Data(email, code, codeCreatedAt, codeExpiresAt, locale)
) {

    class Data(
        val email: String,
        val code: String,
        val codeCreatedAt: AppDateTime,
        val codeExpiresAt: AppDateTime,
        val locale: Locale
    )

    companion object {

        internal fun create(emailVerification: EmailVerification, locale: Locale): EmailVerificationStartedEvent {
            return EmailVerificationStartedEvent(
                email = emailVerification.email,
                code = emailVerification.code,
                codeCreatedAt = emailVerification.codeCreatedAt,
                codeExpiresAt = emailVerification.codeExpiresAt,
                locale = locale
            )
        }
    }
}
