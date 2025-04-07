package com.ttasjwi.board.system.domain.auth.service

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.domain.auth.model.RefreshToken
import com.ttasjwi.board.system.domain.auth.model.RefreshTokenHolder

interface RefreshTokenManager {

    fun generate(memberId: Long, issuedAt: AppDateTime): RefreshToken
    fun parse(tokenValue: String): RefreshToken

    fun checkCurrentlyValid(
        refreshToken: RefreshToken,
        refreshTokenHolder: RefreshTokenHolder,
        currentTime: AppDateTime
    )

    fun isRefreshRequired(refreshToken: RefreshToken, currentTime: AppDateTime): Boolean
}
