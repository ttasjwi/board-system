package com.ttasjwi.board.system.domain.auth.external

import com.ttasjwi.board.system.common.auth.domain.model.AuthMember
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.domain.auth.model.AccessToken

interface ExternalAccessTokenManager {
    fun generate(authMember: AuthMember, issuedAt: AppDateTime, expiresAt: AppDateTime): AccessToken
    fun parse(tokenValue: String): AccessToken
}
