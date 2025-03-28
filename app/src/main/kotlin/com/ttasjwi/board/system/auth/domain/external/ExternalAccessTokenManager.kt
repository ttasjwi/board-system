package com.ttasjwi.board.system.auth.domain.external

import com.ttasjwi.board.system.auth.domain.model.AccessToken
import com.ttasjwi.board.system.common.auth.domain.model.AuthMember
import java.time.ZonedDateTime

interface ExternalAccessTokenManager {
    fun generate(authMember: AuthMember, issuedAt: ZonedDateTime, expiresAt: ZonedDateTime): AccessToken
    fun parse(tokenValue: String): AccessToken
}
