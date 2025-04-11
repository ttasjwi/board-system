package com.ttasjwi.board.system.auth.domain.service.impl

import com.ttasjwi.board.system.auth.domain.event.LoggedInEvent
import com.ttasjwi.board.system.auth.domain.event.TokenRefreshedEvent
import com.ttasjwi.board.system.common.token.AccessToken
import com.ttasjwi.board.system.auth.domain.model.RefreshToken
import com.ttasjwi.board.system.auth.domain.service.AuthEventCreator
import com.ttasjwi.board.system.common.annotation.component.DomainService

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
