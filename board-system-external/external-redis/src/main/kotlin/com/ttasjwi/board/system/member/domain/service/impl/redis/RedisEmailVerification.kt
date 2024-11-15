package com.ttasjwi.board.system.member.domain.service.impl.redis

import com.ttasjwi.board.system.core.time.TimeRule
import com.ttasjwi.board.system.member.domain.model.EmailVerification
import java.time.ZonedDateTime

class RedisEmailVerification(
    val email: String,
    val code: String,
    val codeCreatedAt: ZonedDateTime,
    val codeExpiresAt: ZonedDateTime,
    val verifiedAt: ZonedDateTime?,
    val verificationExpiresAt: ZonedDateTime?
) {

    companion object {

        fun from(emailVerification: EmailVerification): RedisEmailVerification {
            return RedisEmailVerification(
                email = emailVerification.email.value,
                code = emailVerification.code,
                codeCreatedAt = emailVerification.codeCreatedAt,
                codeExpiresAt = emailVerification.codeExpiresAt,
                verifiedAt = emailVerification.verifiedAt,
                verificationExpiresAt = emailVerification.verificationExpiresAt
            )
        }
    }

    fun restoreDomain(): EmailVerification {
        return EmailVerification.restore(
            email = this.email,
            code = this.code,
            codeCreatedAt = this.codeCreatedAt.withZoneSameInstant(TimeRule.ZONE_ID),
            codeExpiresAt = this.codeExpiresAt.withZoneSameInstant(TimeRule.ZONE_ID),
            verifiedAt = this.verifiedAt?.withZoneSameInstant(TimeRule.ZONE_ID),
            verificationExpiresAt = this.verificationExpiresAt?.withZoneSameInstant(TimeRule.ZONE_ID)
        )
    }
}
