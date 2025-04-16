package com.ttasjwi.board.system.common.auth

import com.ttasjwi.board.system.common.time.AppDateTime

interface AccessTokenGeneratePort {
    fun generate(authUser: AuthUser, issuedAt: AppDateTime, expiresAt: AppDateTime): AccessToken
}
