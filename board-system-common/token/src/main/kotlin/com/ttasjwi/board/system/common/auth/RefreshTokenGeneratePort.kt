package com.ttasjwi.board.system.common.auth

import com.ttasjwi.board.system.common.time.AppDateTime

interface RefreshTokenGeneratePort {
    fun generate(userId: Long, refreshTokenId: Long, issuedAt: AppDateTime, expiresAt: AppDateTime): RefreshToken
}
