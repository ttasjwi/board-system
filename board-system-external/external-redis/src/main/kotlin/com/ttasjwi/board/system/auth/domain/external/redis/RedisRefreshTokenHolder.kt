package com.ttasjwi.board.system.auth.domain.external.redis

import com.ttasjwi.board.system.auth.domain.model.RefreshToken
import com.ttasjwi.board.system.auth.domain.model.RefreshTokenHolder
import com.ttasjwi.board.system.auth.domain.model.RefreshTokenId
import java.time.ZoneId
import java.time.ZonedDateTime

class RedisRefreshTokenHolder(
    val authMember: RedisAuthMember,
    val tokens: Map<String, RedisRefreshToken> = mutableMapOf()
) {

    class RedisAuthMember(
        val memberId: Long,
        val email: String,
        val nickname: String,
        val username: String,
        val roleName: String
    )

    class RedisRefreshToken(
        val memberId: Long,
        val refreshTokenId: String,
        val tokenValue: String,
        val issuedAt: ZonedDateTime,
        val expiresAt: ZonedDateTime
    )

    companion object {

        private val TIME_ZONE = ZoneId.of("Asia/Seoul")

        fun from(refreshTokenHolder: RefreshTokenHolder): RedisRefreshTokenHolder {
            return RedisRefreshTokenHolder(
                authMember = RedisAuthMember(
                    memberId = refreshTokenHolder.authMember.memberId.value,
                    email = refreshTokenHolder.authMember.email.value,
                    nickname = refreshTokenHolder.authMember.nickname.value,
                    username = refreshTokenHolder.authMember.username.value,
                    roleName = refreshTokenHolder.authMember.role.name
                ),
                tokens = refreshTokenHolder.getTokens().map { (refreshTokenId, token) ->
                    val key: String = refreshTokenId.value
                    val value = RedisRefreshToken(
                        memberId = token.memberId.value,
                        refreshTokenId = token.refreshTokenId.value,
                        tokenValue = token.tokenValue,
                        issuedAt = token.issuedAt,
                        expiresAt = token.expiresAt
                    )
                    key to value
                }.toMap()
            )
        }
    }

    fun restoreDomain(): RefreshTokenHolder {
        return RefreshTokenHolder.restore(
            memberId = authMember.memberId,
            email = authMember.email,
            nickname = authMember.nickname,
            username = authMember.username,
            roleName = authMember.roleName,
            tokens = tokens.map { (refreshTokenId, token) ->
                val key: RefreshTokenId = RefreshTokenId.restore(refreshTokenId)
                val value = RefreshToken.restore(
                    memberId = token.memberId,
                    refreshTokenId = token.refreshTokenId,
                    tokenValue = token.tokenValue,
                    issuedAt = token.issuedAt.withZoneSameInstant(TIME_ZONE),
                    expiresAt = token.expiresAt.withZoneSameInstant(TIME_ZONE)
                )
                key to value
            }.toMap().toMutableMap()
        )
    }
}
