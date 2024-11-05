package com.ttasjwi.board.system.member.domain.model

import java.time.ZonedDateTime
import java.util.UUID

class EmailVerification
internal constructor(
    val email: Email,
    val code: String,
    val codeCreatedAt: ZonedDateTime,
    val codeExpiresAt: ZonedDateTime,
    verifiedAt: ZonedDateTime? = null,
    verificationExpiresAt: ZonedDateTime? = null
) {

    var verifiedAt: ZonedDateTime? = verifiedAt
        private set

    var verificationExpiresAt: ZonedDateTime? = verificationExpiresAt
        private set


    companion object {

        internal const val CODE_LENGTH = 6
        internal const val CODE_VALIDITY_MINUTE = 5L

        fun restore(
            email: String,
            code: String,
            codeCreatedAt: ZonedDateTime,
            codeExpiresAt: ZonedDateTime,
            verifiedAt: ZonedDateTime?,
            verificationExpiresAt: ZonedDateTime?
        ): EmailVerification {
            return EmailVerification(
                email = Email.restore(email),
                code = code,
                codeCreatedAt = codeCreatedAt,
                codeExpiresAt = codeExpiresAt,
                verifiedAt = verifiedAt,
                verificationExpiresAt = verificationExpiresAt
            )
        }

        internal fun create(email: Email, currentTime: ZonedDateTime): EmailVerification {
            return EmailVerification(
                email = email,
                code = UUID.randomUUID().toString().substring(startIndex = 0, endIndex = CODE_LENGTH),
                codeCreatedAt = currentTime,
                codeExpiresAt = currentTime.plusMinutes(CODE_VALIDITY_MINUTE)
            )
        }
    }
}
