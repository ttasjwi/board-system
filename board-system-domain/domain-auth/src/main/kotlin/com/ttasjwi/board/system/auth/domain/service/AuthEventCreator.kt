package com.ttasjwi.board.system.auth.domain.service

import com.ttasjwi.board.system.auth.domain.event.LoggedInEvent
import com.ttasjwi.board.system.auth.domain.event.TokenRefreshedEvent
import com.ttasjwi.board.system.auth.domain.model.AccessToken
import com.ttasjwi.board.system.auth.domain.model.RefreshToken

interface AuthEventCreator {

    fun onLoginSuccess(accessToken: AccessToken, refreshToken: RefreshToken): LoggedInEvent
    fun onTokenRefreshed(accessToken: AccessToken, refreshToken: RefreshToken, refreshTokenRefreshed: Boolean): TokenRefreshedEvent
}
