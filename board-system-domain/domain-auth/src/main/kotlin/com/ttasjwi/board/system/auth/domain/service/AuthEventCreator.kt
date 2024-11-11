package com.ttasjwi.board.system.auth.domain.service

import com.ttasjwi.board.system.auth.domain.event.LoggedInEvent
import com.ttasjwi.board.system.auth.domain.model.AccessToken
import com.ttasjwi.board.system.auth.domain.model.RefreshToken
import com.ttasjwi.board.system.member.domain.model.MemberId

interface AuthEventCreator {

    fun onLoginSuccess(memberId: MemberId, accessToken: AccessToken, refreshToken: RefreshToken): LoggedInEvent
}
