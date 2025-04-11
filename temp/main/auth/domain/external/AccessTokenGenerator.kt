package com.ttasjwi.board.system.auth.domain.external

import com.ttasjwi.board.system.common.token.AccessToken
import com.ttasjwi.board.system.common.auth.AuthMember
import com.ttasjwi.board.system.common.time.AppDateTime

interface AccessTokenGenerator {
    fun generate(authMember: AuthMember, issuedAt: AppDateTime, expiresAt: AppDateTime): AccessToken
}
