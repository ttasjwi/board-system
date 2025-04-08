package com.ttasjwi.board.system.auth.domain.service.impl

import com.ttasjwi.board.system.auth.domain.external.ExternalRefreshTokenManager
import com.ttasjwi.board.system.auth.domain.model.RefreshToken
import com.ttasjwi.board.system.auth.domain.model.RefreshTokenHolder
import com.ttasjwi.board.system.auth.domain.service.RefreshTokenManager
import com.ttasjwi.board.system.common.annotation.component.DomainService
import com.ttasjwi.board.system.common.time.AppDateTime
import java.util.*

@DomainService
internal class RefreshTokenManagerImpl(
    private val externalRefreshTokenManager: ExternalRefreshTokenManager,
) : RefreshTokenManager {

    companion object {
        internal const val REFRESH_TOKEN_ID_LENGTH = 6
    }

    override fun generate(memberId: Long, issuedAt: AppDateTime): RefreshToken {
        val refreshTokenId = generateRefreshTokenId()
        val expiresAt = issuedAt.plusHours(RefreshToken.VALIDITY_HOURS)
        return externalRefreshTokenManager.generate(memberId, refreshTokenId, issuedAt, expiresAt)
    }

    private fun generateRefreshTokenId(): String {
        return UUID.randomUUID()
            .toString()
            .replace("-", "")
            .substring(0, REFRESH_TOKEN_ID_LENGTH)
    }

    override fun parse(tokenValue: String): RefreshToken {
        return externalRefreshTokenManager.parse(tokenValue)
    }

    override fun checkCurrentlyValid(
        refreshToken: RefreshToken,
        refreshTokenHolder: RefreshTokenHolder,
        currentTime: AppDateTime
    ) {
        // 리프레시 토큰 현재 유효성 검증
        refreshToken.checkCurrentlyValid(currentTime)

        // 리프레시토큰이 리프레시토큰 홀더에 있는 지 검증
        refreshTokenHolder.checkRefreshTokenExist(refreshToken)
    }

    override fun isRefreshRequired(refreshToken: RefreshToken, currentTime: AppDateTime): Boolean {
        return refreshToken.isRefreshRequired(currentTime)
    }
}
