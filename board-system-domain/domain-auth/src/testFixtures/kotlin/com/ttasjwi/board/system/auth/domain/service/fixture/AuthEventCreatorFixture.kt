package com.ttasjwi.board.system.auth.domain.service.fixture

import com.ttasjwi.board.system.auth.domain.event.LoggedInEvent
import com.ttasjwi.board.system.auth.domain.event.fixture.loggedInEventFixture
import com.ttasjwi.board.system.auth.domain.model.AccessToken
import com.ttasjwi.board.system.auth.domain.model.RefreshToken
import com.ttasjwi.board.system.auth.domain.service.AuthEventCreator
import com.ttasjwi.board.system.member.domain.model.MemberId

class AuthEventCreatorFixture : AuthEventCreator {

    override fun onLoginSuccess(memberId: MemberId, accessToken: AccessToken, refreshToken: RefreshToken): LoggedInEvent {
        return loggedInEventFixture(
            accessToken = accessToken.tokenValue,
            accessTokenExpiresAt = accessToken.expiresAt,
            refreshToken = refreshToken.tokenValue,
            refreshTokenExpiresAt = refreshToken.expiresAt,
            loggedInAt = accessToken.issuedAt
        )
    }
}
