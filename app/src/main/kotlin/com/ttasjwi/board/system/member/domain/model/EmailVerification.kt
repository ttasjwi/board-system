package com.ttasjwi.board.system.member.domain.model

import com.ttasjwi.board.system.common.logging.getLogger
import com.ttasjwi.board.system.member.domain.exception.EmailNotVerifiedException
import com.ttasjwi.board.system.member.domain.exception.EmailVerificationExpiredException
import com.ttasjwi.board.system.member.domain.exception.InvalidEmailVerificationCodeException
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

        private val log = getLogger(EmailVerification::class.java)

        internal const val CODE_LENGTH = 6
        internal const val CODE_VALIDITY_MINUTE = 5L
        internal const val VERIFICATION_VALIDITY_MINUTE = 30L

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

    internal fun codeVerify(code: String, currentTime: ZonedDateTime): EmailVerification {
        if (currentTime >= this.codeExpiresAt) {
            log.warn { "이메일 인증이 만료됐습니다. (email=${email.value},expiredAt=${codeExpiresAt},currentTime=${currentTime}" }
            throw EmailVerificationExpiredException(email.value, codeExpiresAt, currentTime)
        }
        if (this.code != code) {
            log.warn { "잘못된 code 입니다." }
            throw InvalidEmailVerificationCodeException()
        }
        this.verifiedAt = currentTime
        this.verificationExpiresAt = currentTime.plusMinutes(VERIFICATION_VALIDITY_MINUTE)

        log.info { "이메일 인증 성공 (email=${email.value}" }
        return this
    }

    internal fun checkVerifiedAndCurrentlyValid(currentTime: ZonedDateTime) {
        log.info{ "이메일 인증이 현재 유효한 지 확인합니다." }

        // 인증이 안 됨 -> 다시 인증 해라
        if (this.verifiedAt == null) {
            log.warn{ "해당 이메일은 인증이 되지 않았음. (email=${this.email.value})" }
            throw EmailNotVerifiedException(email.value)
        }
        // 인증은 했는데, 인증이 만료된 경우 -> 처음부터 다시 인증해라
        if (currentTime >= this.verificationExpiresAt!!) {
            log.warn{ "이메일 인증이 만료됐음. 다시 인증해야합니다. (email=${email.value},expiredAt=${this.verificationExpiresAt!!},currentTime=${currentTime})"}
            throw EmailVerificationExpiredException(email.value, verificationExpiresAt!!, currentTime)
        }
        // 그 외: 인증이 됐고, 인증이 만료되지 않은 경우(유효함)
        log.info{ "이메일 인증이 현재 유효합니다." }
    }
}
