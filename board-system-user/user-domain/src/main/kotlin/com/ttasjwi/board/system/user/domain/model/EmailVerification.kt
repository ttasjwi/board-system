package com.ttasjwi.board.system.user.domain.model

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.user.domain.exception.EmailNotVerifiedException
import com.ttasjwi.board.system.user.domain.exception.EmailVerificationExpiredException
import com.ttasjwi.board.system.user.domain.exception.InvalidEmailVerificationCodeException
import java.time.Instant
import java.util.*

class EmailVerification
internal constructor(
    val email: String,
    val code: String,
    val codeCreatedAt: AppDateTime,
    val codeExpiresAt: AppDateTime,
    verifiedAt: AppDateTime? = null,
    verificationExpiresAt: AppDateTime? = null
) {

    var verifiedAt: AppDateTime? = verifiedAt
        private set

    var verificationExpiresAt: AppDateTime? = verificationExpiresAt
        private set

    companion object {

        const val CODE_LENGTH = 6
        const val CODE_VALIDITY_MINUTE = 5L
        const val VERIFICATION_VALIDITY_MINUTE = 30L

        fun create(email: String, currentTime: AppDateTime): EmailVerification {
            return EmailVerification(
                email = email,
                code = UUID.randomUUID().toString().substring(startIndex = 0, endIndex = CODE_LENGTH),
                codeCreatedAt = currentTime,
                codeExpiresAt = currentTime.plusMinutes(CODE_VALIDITY_MINUTE)
            )
        }

        fun restore(
            email: String,
            code: String,
            codeCreatedAt: Instant,
            codeExpiresAt: Instant,
            verifiedAt: Instant?,
            verificationExpiresAt: Instant?
        ): EmailVerification {
            return EmailVerification(
                email = email,
                code = code,
                codeCreatedAt = AppDateTime.from(codeCreatedAt),
                codeExpiresAt = AppDateTime.from(codeExpiresAt),
                verifiedAt = verifiedAt?.let { AppDateTime.from(it) },
                verificationExpiresAt = verificationExpiresAt?.let { AppDateTime.from(it) }
            )
        }
    }

    fun codeVerify(code: String, currentTime: AppDateTime): EmailVerification {
        if (currentTime >= this.codeExpiresAt) {
            throw EmailVerificationExpiredException(email, codeExpiresAt, currentTime)
        }
        if (this.code != code) {
            throw InvalidEmailVerificationCodeException()
        }
        this.verifiedAt = currentTime
        this.verificationExpiresAt = currentTime.plusMinutes(VERIFICATION_VALIDITY_MINUTE)

        return this
    }

    fun throwIfNotVerifiedOrCurrentlyNotValid(currentTime: AppDateTime) {
        // 인증이 안 됨 -> 다시 인증 해라
        if (this.verifiedAt == null) {
            throw EmailNotVerifiedException(email)
        }
        // 인증은 했는데, 인증이 만료된 경우 -> 처음부터 다시 인증해라
        if (currentTime >= this.verificationExpiresAt!!) {
            throw EmailVerificationExpiredException(email, verificationExpiresAt!!, currentTime)
        }
    }
}
