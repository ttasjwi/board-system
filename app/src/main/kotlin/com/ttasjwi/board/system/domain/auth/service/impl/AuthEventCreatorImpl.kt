package com.ttasjwi.board.system.domain.auth.service.impl

import com.ttasjwi.board.system.common.annotation.component.DomainService
import com.ttasjwi.board.system.domain.auth.event.LoggedInEvent
import com.ttasjwi.board.system.domain.auth.event.TokenRefreshedEvent
import com.ttasjwi.board.system.domain.auth.model.AccessToken
import com.ttasjwi.board.system.domain.auth.model.RefreshToken
import com.ttasjwi.board.system.domain.auth.service.AuthEventCreator

@DomainService
internal class AuthEventCreatorImpl : AuthEventCreator {

    override fun onLoginSuccess(accessToken: AccessToken, refreshToken: RefreshToken): LoggedInEvent {
        return LoggedInEvent.create(accessToken, refreshToken)
    }

    override fun onTokenRefreshed(
        accessToken: AccessToken,
        refreshToken: RefreshToken,
        refreshTokenRefreshed: Boolean
    ): TokenRefreshedEvent {
        return TokenRefreshedEvent(
            accessToken = accessToken.tokenValue,
            accessTokenExpiresAt = accessToken.expiresAt,
            refreshToken = refreshToken.tokenValue,
            refreshTokenExpiresAt = refreshToken.expiresAt,
            refreshTokenRefreshed = refreshTokenRefreshed,
            refreshedAt = accessToken.issuedAt
        )
    }
}
