package com.ttasjwi.board.system.auth.domain.model

import java.time.ZonedDateTime

class AccessToken
internal constructor(
    val authMember: AuthMember,
    val tokenValue: String,
    val issuedAt: ZonedDateTime,
    val expiresAt: ZonedDateTime,
) {

    companion object {

        fun restore(
            memberId: Long,
            email: String,
            username: String,
            nickname: String,
            roleName: String,
            tokenValue: String,
            issuedAt: ZonedDateTime,
            expiresAt: ZonedDateTime,
        ): AccessToken {
            return AccessToken(
                authMember = AuthMember.restore(
                    memberId = memberId,
                    email = email,
                    username = username,
                    nickname = nickname,
                    roleName = roleName,
                ),
                tokenValue = tokenValue,
                issuedAt = issuedAt,
                expiresAt = expiresAt
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

}
