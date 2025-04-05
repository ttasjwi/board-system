package com.ttasjwi.board.system.member.domain.model

import com.ttasjwi.board.system.global.logging.getLogger
import com.ttasjwi.board.system.global.time.AppDateTime
import com.ttasjwi.board.system.member.domain.exception.EmailNotVerifiedException
import com.ttasjwi.board.system.member.domain.exception.EmailVerificationExpiredException
import com.ttasjwi.board.system.member.domain.exception.InvalidEmailVerificationCodeException
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

        private val log = getLogger(EmailVerification::class.java)

        internal const val CODE_LENGTH = 6
        internal const val CODE_VALIDITY_MINUTE = 5L
        internal const val VERIFICATION_VALIDITY_MINUTE = 30L

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

        internal fun create(email: String, currentTime: AppDateTime): EmailVerification {
            return EmailVerification(
                email = email,
                code = UUID.randomUUID().toString().substring(startIndex = 0, endIndex = CODE_LENGTH),
                codeCreatedAt = currentTime,
                codeExpiresAt = currentTime.plusMinutes(CODE_VALIDITY_MINUTE)
            )
        }
    }

    internal fun codeVerify(code: String, currentTime: AppDateTime): EmailVerification {
        if (currentTime >= this.codeExpiresAt) {
            log.warn { "이메일 인증이 만료됐습니다. (email=${email},expiredAt=${codeExpiresAt},currentTime=${currentTime}" }
            throw EmailVerificationExpiredException(email, codeExpiresAt, currentTime)
        }
        if (this.code != code) {
            log.warn { "잘못된 code 입니다." }
            throw InvalidEmailVerificationCodeException()
        }
        this.verifiedAt = currentTime
        this.verificationExpiresAt = currentTime.plusMinutes(VERIFICATION_VALIDITY_MINUTE)

        log.info { "이메일 인증 성공 (email=$email)" }
        return this
    }

    internal fun checkVerifiedAndCurrentlyValid(currentTime: AppDateTime) {
        log.info{ "이메일 인증이 현재 유효한 지 확인합니다." }

        // 인증이 안 됨 -> 다시 인증 해라
        if (this.verifiedAt == null) {
            log.warn{ "해당 이메일은 인증이 되지 않았음. (email=${this.email})" }
            throw EmailNotVerifiedException(email)
        }
        // 인증은 했는데, 인증이 만료된 경우 -> 처음부터 다시 인증해라
        if (currentTime >= this.verificationExpiresAt!!) {
            log.warn{ "이메일 인증이 만료됐음. 다시 인증해야합니다. (email=${email},expiredAt=${this.verificationExpiresAt!!},currentTime=${currentTime})"}
            throw EmailVerificationExpiredException(email, verificationExpiresAt!!, currentTime)
        }
        // 그 외: 인증이 됐고, 인증이 만료되지 않은 경우(유효함)
        log.info{ "이메일 인증이 현재 유효합니다." }
    }
}
