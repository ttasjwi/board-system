package com.ttasjwi.board.system.domain.auth.service

import com.ttasjwi.board.system.domain.auth.event.LoggedInEvent
import com.ttasjwi.board.system.domain.auth.event.TokenRefreshedEvent
import com.ttasjwi.board.system.domain.auth.model.AccessToken
import com.ttasjwi.board.system.domain.auth.model.RefreshToken

interface AuthEventCreator {
    fun onLoginSuccess(accessToken: AccessToken, refreshToken: RefreshToken): LoggedInEvent
    fun onTokenRefreshed(
        accessToken: AccessToken,
        refreshToken: RefreshToken,
        refreshTokenRefreshed: Boolean
    ): TokenRefreshedEvent
}
