package com.ttasjwi.board.system.common.auth

import com.ttasjwi.board.system.common.time.AppDateTime
import java.time.Instant

class AccessToken
internal constructor(
    val authUser: AuthUser,
    val tokenType: String = VALID_TOKEN_TYPE,
    val tokenValue: String,
    val issuer: String = VALID_ISSUER,
    val issuedAt: AppDateTime,
    val expiresAt: AppDateTime,
) {

    companion object {

        const val VALID_TOKEN_TYPE = "AccessToken"
        const val VALID_ISSUER = "BoardSystem"

        /**
         * 액세스 토큰이 최초로 발급되는 시점에만 호출되어야 합니다.
         * 이 메서드는 신뢰할 수 있는 시스템(예: 인증 서버)에서 AccessToken 객체를 생성할 때 사용됩니다.
         * 토큰 값, 발급 시각, 만료 시각 등은 이미 적절히 생성된 상태라고 가정하며, 이 메서드는 순수하게 도메인 객체를 생성만 합니다.
         */
        fun create(
            authUser: AuthUser,
            tokenValue: String,
            issuedAt: AppDateTime,
            expiresAt: AppDateTime,
        ): AccessToken {
            return AccessToken(
                authUser = authUser,
                tokenValue = tokenValue,
                tokenType = VALID_TOKEN_TYPE,
                issuer = VALID_ISSUER,
                issuedAt = issuedAt,
                expiresAt = expiresAt
            )
        }

        /**
         * 외부에서 액세스 토큰의 유효성 검증이 끝난 경우에만 호출되어야 합니다.
         * 예: 서명 검증, 만료 시간 확인 등이 선행되어야 함.
         * 이 메서드는 순수하게 AccessToken 객체를 생성만 하며, 내부에서는 어떠한 유효성 검사도 수행하지 않습니다.
         */
        fun restore(
            userId: Long,
            roleName: String,
            tokenValue: String,
            issuedAt: Instant,
            expiresAt: Instant,
        ): AccessToken {
            return AccessToken(
                authUser = AuthUser.restore(
                    userId = userId,
                    roleName = roleName,
                ),
                tokenType = VALID_TOKEN_TYPE,
                tokenValue = tokenValue,
                issuer = VALID_ISSUER,
                issuedAt = AppDateTime.from(issuedAt),
                expiresAt = AppDateTime.from(expiresAt),
            )
        }
    }

    /**
     * 현재 시간을 전달받아, 토큰이 만료된 경우 예외를 발생시킵니다.
     */
    fun throwIfExpired(currentTime: AppDateTime) {
        if (currentTime >= this.expiresAt) {
            throw AccessTokenExpiredException(expiredAt = this.expiresAt, currentTime = currentTime)
        }
    }
}
