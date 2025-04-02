package com.ttasjwi.board.system.auth.domain.external.impl.redis

import com.ttasjwi.board.system.auth.domain.model.RefreshToken
import com.ttasjwi.board.system.auth.domain.model.RefreshTokenHolder
import com.ttasjwi.board.system.auth.domain.model.RefreshTokenId
import java.time.Instant

class RedisRefreshTokenHolder(
    val authMember: RedisAuthMember,
    val tokens: Map<String, RedisRefreshToken> = mutableMapOf()
) {

    class RedisAuthMember(
        val memberId: Long,
        val roleName: String
    )

    class RedisRefreshToken(
        val memberId: Long,
        val refreshTokenId: String,
        val tokenValue: String,
        val issuedAt: Instant,
        val expiresAt: Instant
    )

    companion object {

        fun from(refreshTokenHolder: RefreshTokenHolder): RedisRefreshTokenHolder {
            return RedisRefreshTokenHolder(
                authMember = RedisAuthMember(
                    memberId = refreshTokenHolder.authMember.memberId,
                    roleName = refreshTokenHolder.authMember.role.name
                ),
                tokens = refreshTokenHolder.getTokens().map { (refreshTokenId, token) ->
                    val key: String = refreshTokenId.value
                    val value = RedisRefreshToken(
                        memberId = token.memberId,
                        refreshTokenId = token.refreshTokenId.value,
                        tokenValue = token.tokenValue,
                        issuedAt = token.issuedAt.toInstant(),
                        expiresAt = token.expiresAt.toInstant()
                    )
                    key to value
                }.toMap()
            )
        }
    }

    fun restoreDomain(): RefreshTokenHolder {
        return RefreshTokenHolder.restore(
            memberId = authMember.memberId,
            roleName = authMember.roleName,
            tokens = tokens.map { (refreshTokenId, token) ->
                val key: RefreshTokenId = RefreshTokenId.restore(refreshTokenId)
                val value = RefreshToken.restore(
                    memberId = token.memberId,
                    refreshTokenId = token.refreshTokenId,
                    tokenValue = token.tokenValue,
                    issuedAt = token.issuedAt,
                    expiresAt = token.expiresAt
                )
                key to value
            }.toMap().toMutableMap()
        )
    }
}
