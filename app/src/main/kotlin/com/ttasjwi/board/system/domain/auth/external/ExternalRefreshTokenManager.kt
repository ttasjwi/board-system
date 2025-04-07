package com.ttasjwi.board.system.domain.auth.external

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.domain.auth.model.RefreshToken

interface ExternalRefreshTokenManager {

    fun generate(
        memberId: Long,
        refreshTokenId: String,
        issuedAt: AppDateTime,
        expiresAt: AppDateTime
    ): RefreshToken

    fun parse(tokenValue: String): RefreshToken
}
