package com.ttasjwi.board.system.member.domain.model

import java.time.ZonedDateTime

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
    }
}
