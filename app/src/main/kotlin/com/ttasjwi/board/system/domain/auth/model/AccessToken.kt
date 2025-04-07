package com.ttasjwi.board.system.domain.auth.model

import com.ttasjwi.board.system.common.auth.domain.model.AuthMember
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.domain.auth.exception.AccessTokenExpiredException
import java.time.Instant

class AccessToken
internal constructor(
    val authMember: AuthMember,
    val tokenValue: String,
    val issuedAt: AppDateTime,
    val expiresAt: AppDateTime,
) {

    companion object {

        internal const val VALIDITY_MINUTE = 30L

        fun restore(
            memberId: Long,
            roleName: String,
            tokenValue: String,
            issuedAt: Instant,
            expiresAt: Instant,
        ): AccessToken {
            return AccessToken(
                authMember = AuthMember.restore(
                    memberId = memberId,
                    roleName = roleName,
                ),
                tokenValue = tokenValue,
                issuedAt = AppDateTime.from(issuedAt),
                expiresAt = AppDateTime.from(expiresAt),
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AccessToken) return false
        if (authMember != other.authMember) return false
        if (tokenValue != other.tokenValue) return false
        if (issuedAt != other.issuedAt) return false
        if (expiresAt != other.expiresAt) return false
        return true
    }

    override fun hashCode(): Int {
        var result = authMember.hashCode()
        result = 31 * result + tokenValue.hashCode()
        result = 31 * result + issuedAt.hashCode()
        result = 31 * result + expiresAt.hashCode()
        return result
    }

    internal fun checkCurrentlyValid(currentTime: AppDateTime) {
        if (currentTime >= this.expiresAt) {
            throw AccessTokenExpiredException(expiredAt = this.expiresAt, currentTime = currentTime)
        }
    }
}
