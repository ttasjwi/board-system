package com.ttasjwi.board.system.auth.domain.external

import com.ttasjwi.board.system.auth.domain.model.AccessToken
import com.ttasjwi.board.system.global.auth.AuthMember
import com.ttasjwi.board.system.global.time.AppDateTime

interface ExternalAccessTokenManager {
    fun generate(authMember: AuthMember, issuedAt: AppDateTime, expiresAt: AppDateTime): AccessToken
    fun parse(tokenValue: String): AccessToken
}
