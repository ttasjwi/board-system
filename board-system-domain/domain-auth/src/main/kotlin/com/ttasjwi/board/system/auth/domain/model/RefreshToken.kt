package com.ttasjwi.board.system.auth.domain.model

import com.ttasjwi.board.system.member.domain.model.MemberId
import java.time.ZonedDateTime

class RefreshToken
internal constructor(
    val memberId: MemberId,
    val refreshTokenId: RefreshTokenId,
    val tokenValue: String,
    val issuedAt: ZonedDateTime,
    val expiresAt: ZonedDateTime,
) {

    companion object {
        fun restore(
            memberId: Long,
            refreshTokenId: String,
            tokenValue: String,
            issuedAt: ZonedDateTime,
            expiresAt: ZonedDateTime
        ): RefreshToken {
            return RefreshToken(
                memberId = MemberId.restore(memberId),
                refreshTokenId = RefreshTokenId.restore(refreshTokenId),
                tokenValue = tokenValue,
                issuedAt = issuedAt,
                expiresAt = expiresAt
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RefreshToken) return false
        if (memberId != other.memberId) return false
        if (refreshTokenId != other.refreshTokenId) return false
        if (tokenValue != other.tokenValue) return false
        if (issuedAt != other.issuedAt) return false
        if (expiresAt != other.expiresAt) return false
        return true
    }

    override fun hashCode(): Int {
        var result = memberId.hashCode()
        result = 31 * result + refreshTokenId.hashCode()
        result = 31 * result + tokenValue.hashCode()
        result = 31 * result + issuedAt.hashCode()
        result = 31 * result + expiresAt.hashCode()
        return result
    }
}
