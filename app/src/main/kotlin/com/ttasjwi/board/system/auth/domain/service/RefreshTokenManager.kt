package com.ttasjwi.board.system.auth.domain.service

import com.ttasjwi.board.system.auth.domain.model.RefreshToken
import com.ttasjwi.board.system.auth.domain.model.RefreshTokenHolder
import com.ttasjwi.board.system.global.time.AppDateTime

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
