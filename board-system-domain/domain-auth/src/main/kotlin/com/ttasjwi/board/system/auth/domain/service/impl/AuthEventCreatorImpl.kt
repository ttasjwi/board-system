package com.ttasjwi.board.system.auth.domain.service.impl

import com.ttasjwi.board.system.auth.domain.event.LoggedInEvent
import com.ttasjwi.board.system.auth.domain.model.AccessToken
import com.ttasjwi.board.system.auth.domain.model.RefreshToken
import com.ttasjwi.board.system.auth.domain.service.AuthEventCreator
import com.ttasjwi.board.system.core.annotation.component.DomainService

@DomainService
internal class AuthEventCreatorImpl : AuthEventCreator {

    override fun onLoginSuccess(accessToken: AccessToken, refreshToken: RefreshToken): LoggedInEvent {
        TODO("Not yet implemented")
    }
}
