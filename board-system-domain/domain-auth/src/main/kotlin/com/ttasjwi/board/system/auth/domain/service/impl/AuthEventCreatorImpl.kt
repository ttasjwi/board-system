package com.ttasjwi.board.system.auth.domain.service.impl

import com.ttasjwi.board.system.auth.domain.event.LoggedInEvent
import com.ttasjwi.board.system.auth.domain.model.AccessToken
import com.ttasjwi.board.system.auth.domain.model.RefreshToken
import com.ttasjwi.board.system.auth.domain.service.AuthEventCreator
import com.ttasjwi.board.system.core.annotation.component.DomainService
import com.ttasjwi.board.system.member.domain.model.MemberId

@DomainService
internal class AuthEventCreatorImpl : AuthEventCreator {

    override fun onLoginSuccess(memberId: MemberId, accessToken: AccessToken, refreshToken: RefreshToken): LoggedInEvent {
        TODO("Not yet implemented")
    }
}
