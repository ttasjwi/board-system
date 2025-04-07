package com.ttasjwi.board.system.domain.auth.service

import com.ttasjwi.board.system.common.auth.domain.model.AuthMember
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.domain.auth.model.AccessToken

interface AccessTokenManager {

    fun generate(authMember: AuthMember, issuedAt: AppDateTime): AccessToken
    fun parse(tokenValue: String): AccessToken
    fun checkCurrentlyValid(accessToken: AccessToken, currentTime: AppDateTime)
}
