package com.ttasjwi.board.system.member.domain.service.impl.redis

import com.ttasjwi.board.system.member.domain.model.EmailVerification
import java.time.ZoneId
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

        private val TIME_ZONE = ZoneId.of("Asia/Seoul")

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
            codeCreatedAt = this.codeCreatedAt.withZoneSameInstant(TIME_ZONE),
            codeExpiresAt = this.codeExpiresAt.withZoneSameInstant(TIME_ZONE),
            verifiedAt = this.verifiedAt?.withZoneSameInstant(TIME_ZONE),
            verificationExpiresAt = this.verificationExpiresAt?.withZoneSameInstant(TIME_ZONE)
        )
    }
}
