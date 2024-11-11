package com.ttasjwi.board.system.auth.domain.service

import com.ttasjwi.board.system.auth.domain.model.AccessToken
import com.ttasjwi.board.system.auth.domain.model.AuthMember
import java.time.ZonedDateTime

interface AccessTokenManager {

    fun generate(authMember: AuthMember, issuedAt: ZonedDateTime): AccessToken
    fun parse(tokenValue: String): AccessToken
}
