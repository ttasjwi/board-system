package com.ttasjwi.board.system.domain.auth.service.fixture

import com.ttasjwi.board.system.domain.auth.event.LoggedInEvent
import com.ttasjwi.board.system.domain.auth.event.TokenRefreshedEvent
import com.ttasjwi.board.system.domain.auth.event.fixture.loggedInEventFixture
import com.ttasjwi.board.system.domain.auth.event.fixture.tokenRefreshedEventFixture
import com.ttasjwi.board.system.domain.auth.model.AccessToken
import com.ttasjwi.board.system.domain.auth.model.RefreshToken
import com.ttasjwi.board.system.domain.auth.service.AuthEventCreator

class AuthEventCreatorFixture : AuthEventCreator {

    override fun onLoginSuccess(accessToken: AccessToken, refreshToken: RefreshToken): LoggedInEvent {
        return loggedInEventFixture(
            accessToken = accessToken.tokenValue,
            accessTokenExpiresAt = accessToken.expiresAt,
            refreshToken = refreshToken.tokenValue,
            refreshTokenExpiresAt = refreshToken.expiresAt,
            loggedInAt = accessToken.issuedAt
        )
    }

    override fun onTokenRefreshed(
        accessToken: AccessToken,
        refreshToken: RefreshToken,
        refreshTokenRefreshed: Boolean
    ): TokenRefreshedEvent {
        return tokenRefreshedEventFixture(
            accessToken = accessToken.tokenValue,
            accessTokenExpiresAt = accessToken.expiresAt,
            refreshToken = refreshToken.tokenValue,
            refreshTokenExpiresAt = refreshToken.expiresAt,
            refreshedAt = accessToken.issuedAt,
            refreshTokenRefreshed = refreshTokenRefreshed
        )
    }
}
