package com.ttasjwi.board.system.member.domain.event

import com.ttasjwi.board.system.common.domain.event.DomainEvent
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.member.domain.model.EmailVerification
import java.time.ZonedDateTime
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
                email = emailVerification.email.value,
                code = emailVerification.code,
                codeCreatedAt = emailVerification.codeCreatedAt,
                codeExpiresAt = emailVerification.codeExpiresAt,
                locale = locale
            )
        }
    }
}
