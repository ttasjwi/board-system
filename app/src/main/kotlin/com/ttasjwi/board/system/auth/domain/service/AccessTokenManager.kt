package com.ttasjwi.board.system.auth.domain.service

import com.ttasjwi.board.system.auth.domain.model.AccessToken
import com.ttasjwi.board.system.common.auth.domain.model.AuthMember
import com.ttasjwi.board.system.common.time.AppDateTime

interface AccessTokenManager {

    fun generate(authMember: AuthMember, issuedAt: AppDateTime): AccessToken
    fun parse(tokenValue: String): AccessToken
    fun checkCurrentlyValid(accessToken: AccessToken, currentTime: AppDateTime)
}
