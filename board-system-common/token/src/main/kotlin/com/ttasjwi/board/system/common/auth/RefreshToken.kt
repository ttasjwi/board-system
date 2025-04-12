package com.ttasjwi.board.system.common.auth

import com.ttasjwi.board.system.common.time.AppDateTime
import java.time.Instant

class RefreshToken
internal constructor(
    val memberId: Long,
    val refreshTokenId: Long,
    val tokenType: String,
    val tokenValue: String,
    val issuer: String,
    val issuedAt: AppDateTime,
    val expiresAt: AppDateTime,
) {

    companion object {
        const val VALID_TOKEN_TYPE = "RefreshToken"
        const val VALID_ISSUER = "BoardSystem"

        fun create(
            memberId: Long,
            refreshTokenId: Long,
            issuedAt: AppDateTime,
            expiresAt: AppDateTime,
            tokenValue: String,
        ): RefreshToken {
            return RefreshToken(
                memberId = memberId,
                refreshTokenId = refreshTokenId,
                tokenType = VALID_TOKEN_TYPE,
                tokenValue = tokenValue,
                issuer = VALID_ISSUER,
                issuedAt = issuedAt,
                expiresAt = expiresAt
            )
        }

        fun restore(
            memberId: Long,
            refreshTokenId: Long,
            tokenValue: String,
            issuedAt: Instant,
            expiresAt: Instant
        ): RefreshToken {
            return RefreshToken(
                memberId = memberId,
                refreshTokenId = refreshTokenId,
                tokenType = VALID_TOKEN_TYPE,
                tokenValue = tokenValue,
                issuer = VALID_ISSUER,
                issuedAt = AppDateTime.from(issuedAt),
                expiresAt = AppDateTime.from(expiresAt)
            )
        }
    }

    fun isExpired(currentTime: AppDateTime): Boolean {
        return expiresAt <= currentTime
    }
}
