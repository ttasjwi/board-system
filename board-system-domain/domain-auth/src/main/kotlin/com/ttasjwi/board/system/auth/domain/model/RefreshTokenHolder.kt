package com.ttasjwi.board.system.auth.domain.model

import com.ttasjwi.board.system.auth.domain.exception.RefreshTokenExpiredException
import com.ttasjwi.board.system.core.time.TimeRule
import com.ttasjwi.board.system.logging.getLogger
import java.time.LocalDateTime
import java.time.ZonedDateTime

/**
 * 회원 및 회원이 가진 리프레시 토큰 목록을 관리하는 객체입니다.
 */
class RefreshTokenHolder
internal constructor(
    val authMember: AuthMember,
    tokens: MutableMap<RefreshTokenId, RefreshToken>
) {

    private val _tokens: MutableMap<RefreshTokenId, RefreshToken> = tokens

    companion object {

        internal const val MAX_TOKEN_COUNT = 5

        private val log = getLogger(RefreshTokenHolder::class.java)

        internal fun create(authMember: AuthMember): RefreshTokenHolder {
            return RefreshTokenHolder(
                authMember = authMember,
                tokens = hashMapOf()
            )
        }

        fun restore(
            memberId: Long,
            roleName: String,
            tokens: MutableMap<RefreshTokenId, RefreshToken>
        ): RefreshTokenHolder {
            return RefreshTokenHolder(
                authMember = AuthMember.restore(
                    memberId = memberId,
                    roleName = roleName,
                ),
                tokens = tokens,
            )
        }
    }

    internal fun addNewRefreshToken(refreshToken: RefreshToken): RefreshTokenHolder {
        // 리프레시토큰의 발행시점을 현재 시각으로 삼아, 오래된 토큰들을 만료시킴
        val currentTime = refreshToken.issuedAt
        removeExpiredTokens(currentTime)

        if (_tokens.size == MAX_TOKEN_COUNT) {
            // 보유할 수 있는 토큰의 갯수가 제한을 초과하면 가장 발행이 먼저된 것을 만료시킴
            val oldestToken = _tokens.values.minBy { it.issuedAt }
            _tokens.remove(oldestToken.refreshTokenId)
        }
        _tokens[refreshToken.refreshTokenId] = refreshToken
        return this
    }

    private fun removeExpiredTokens(currentTime: ZonedDateTime) {
        _tokens.entries.removeIf { currentTime >= it.value.expiresAt }
    }

    fun getTokens(): Map<RefreshTokenId, RefreshToken> {
        return _tokens.toMap()
    }

    internal fun expiresAt(currentTime: ZonedDateTime): ZonedDateTime {
        // 토큰이 없으면 지금이 만료시점
        if (_tokens.isEmpty()) {
            return currentTime
        }
        // 만료일이 가장 늦은 것을 기준으로 만료시킴
        // 가장 만료시간이 마지막인 토큰을 기준으로 만료시간을 잡음
        var maxExpireTime = ZonedDateTime.of(LocalDateTime.MIN, TimeRule.ZONE_ID)
        for (token in _tokens.values) {
            if (token.expiresAt > maxExpireTime) {
                maxExpireTime = token.expiresAt
            }
        }
        return maxExpireTime
    }

    /**
     * 같은 리프레시토큰이 있는지 검증. 없다면 리프레시토큰이 무효화된 것
     */
    internal fun checkRefreshTokenExist(refreshToken: RefreshToken) {
        // 토큰이 없을 때
        if (!_tokens.containsKey(refreshToken.refreshTokenId)) {
            val ex = RefreshTokenExpiredException(
                debugMessage = "리프레시 토큰이 로그아웃 또는 동시토큰 제한 등의 이유로 토큰이 만료됨. (memberId=${refreshToken.memberId.value},refreshTokenId=${refreshToken.refreshTokenId.value})"
            )
            log.warn(ex)
            throw ex
        }
    }

    internal fun changeRefreshToken(previousToken: RefreshToken, newToken: RefreshToken): RefreshTokenHolder {
        // 새로 추가시키는 리프레시토큰의 발행시점을 현재 시각으로 삼아, 오래된 토큰들을 만료시킴
        val currentTime = newToken.issuedAt
        removeExpiredTokens(currentTime)

        // 기존 토큰 제거
        _tokens.remove(previousToken.refreshTokenId)

        // 신규 토큰 추가
        _tokens[newToken.refreshTokenId] = newToken
        return this
    }
}
