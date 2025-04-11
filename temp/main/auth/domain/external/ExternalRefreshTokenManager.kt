package com.ttasjwi.board.system.auth.domain.external

import com.ttasjwi.board.system.auth.domain.model.RefreshToken
import com.ttasjwi.board.system.common.time.AppDateTime

interface ExternalRefreshTokenManager {

    fun generate(
        memberId: Long,
        refreshTokenId: String,
        issuedAt: AppDateTime,
        expiresAt: AppDateTime
    ): RefreshToken

    fun parse(tokenValue: String): RefreshToken
}
