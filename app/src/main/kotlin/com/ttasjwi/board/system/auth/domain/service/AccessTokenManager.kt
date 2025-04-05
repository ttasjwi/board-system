package com.ttasjwi.board.system.auth.domain.service

import com.ttasjwi.board.system.auth.domain.model.AccessToken
import com.ttasjwi.board.system.global.auth.AuthMember
import com.ttasjwi.board.system.global.time.AppDateTime

interface AccessTokenManager {

    fun generate(authMember: AuthMember, issuedAt: AppDateTime): AccessToken
    fun parse(tokenValue: String): AccessToken
    fun checkCurrentlyValid(accessToken: AccessToken, currentTime: AppDateTime)
}
