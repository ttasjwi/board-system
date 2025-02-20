package com.ttasjwi.board.system.auth.domain.service.fixture

import com.ttasjwi.board.system.auth.domain.event.LoggedInEvent
import com.ttasjwi.board.system.auth.domain.event.TokenRefreshedEvent
import com.ttasjwi.board.system.auth.domain.event.fixture.loggedInEventFixture
import com.ttasjwi.board.system.auth.domain.event.fixture.tokenRefreshedEventFixture
import com.ttasjwi.board.system.auth.domain.model.AccessToken
import com.ttasjwi.board.system.auth.domain.model.RefreshToken
import com.ttasjwi.board.system.auth.domain.service.AuthEventCreator

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
