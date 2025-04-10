package com.ttasjwi.board.system.member.infra.persistence.redis

import com.ttasjwi.board.system.member.domain.model.EmailVerification
import java.time.Instant

class RedisEmailVerification(
    val email: String,
    val code: String,
    val codeCreatedAt: Instant,
    val codeExpiresAt: Instant,
    val verifiedAt: Instant?,
    val verificationExpiresAt: Instant?
) {

    companion object {

        fun from(emailVerification: EmailVerification): RedisEmailVerification {
            return RedisEmailVerification(
                email = emailVerification.email,
                code = emailVerification.code,
                codeCreatedAt = emailVerification.codeCreatedAt.toInstant(),
                codeExpiresAt = emailVerification.codeExpiresAt.toInstant(),
                verifiedAt = emailVerification.verifiedAt?.toInstant(),
                verificationExpiresAt = emailVerification.verificationExpiresAt?.toInstant()
            )
        }
    }

    fun restoreDomain(): EmailVerification {
        return EmailVerification.restore(
            email = this.email,
            code = this.code,
            codeCreatedAt = this.codeCreatedAt,
            codeExpiresAt = this.codeExpiresAt,
            verifiedAt = this.verifiedAt,
            verificationExpiresAt = this.verificationExpiresAt
        )
    }
}
