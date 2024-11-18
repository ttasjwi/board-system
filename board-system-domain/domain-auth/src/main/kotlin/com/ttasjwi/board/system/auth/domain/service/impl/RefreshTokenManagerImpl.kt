package com.ttasjwi.board.system.auth.domain.service.impl

import com.ttasjwi.board.system.auth.domain.external.ExternalRefreshTokenManager
import com.ttasjwi.board.system.auth.domain.model.RefreshToken
import com.ttasjwi.board.system.auth.domain.model.RefreshTokenHolder
import com.ttasjwi.board.system.auth.domain.model.RefreshTokenId
import com.ttasjwi.board.system.auth.domain.service.RefreshTokenManager
import com.ttasjwi.board.system.core.annotation.component.DomainService
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
        TODO("Not yet implemented")
    }

    override fun isRefreshRequired(refreshToken: RefreshToken, currentTime: ZonedDateTime): Boolean {
        TODO("Not yet implemented")
    }
}
