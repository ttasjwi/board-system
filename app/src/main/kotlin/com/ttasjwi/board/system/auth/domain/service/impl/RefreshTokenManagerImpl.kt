package com.ttasjwi.board.system.auth.domain.service.impl

import com.ttasjwi.board.system.auth.domain.external.ExternalRefreshTokenManager
import com.ttasjwi.board.system.auth.domain.model.RefreshToken
import com.ttasjwi.board.system.auth.domain.model.RefreshTokenHolder
import com.ttasjwi.board.system.auth.domain.model.RefreshTokenId
import com.ttasjwi.board.system.auth.domain.service.RefreshTokenManager
import com.ttasjwi.board.system.common.annotation.component.DomainService
import com.ttasjwi.board.system.member.domain.model.MemberId
import java.time.ZonedDateTime

@DomainService
internal class RefreshTokenManagerImpl(
    private val externalRefreshTokenManager: ExternalRefreshTokenManager,
) : RefreshTokenManager {

    override fun generate(memberId: MemberId, issuedAt: ZonedDateTime): RefreshToken {
        val refreshTokenId = RefreshTokenId.create()
        val expiresAt = issuedAt.plusHours(RefreshToken.VALIDITY_HOURS)
        return externalRefreshTokenManager.generate(memberId, refreshTokenId, issuedAt, expiresAt)
    }

    override fun parse(tokenValue: String): RefreshToken {
        return externalRefreshTokenManager.parse(tokenValue)
    }

    override fun checkCurrentlyValid(
        refreshToken: RefreshToken,
        refreshTokenHolder: RefreshTokenHolder,
        currentTime: ZonedDateTime
    ) {
        // 리프레시 토큰 현재 유효성 검증
        refreshToken.checkCurrentlyValid(currentTime)

        // 리프레시토큰이 리프레시토큰 홀더에 있는 지 검증
        refreshTokenHolder.checkRefreshTokenExist(refreshToken)
    }

    override fun isRefreshRequired(refreshToken: RefreshToken, currentTime: ZonedDateTime): Boolean {
        return refreshToken.isRefreshRequired(currentTime)
    }
}
