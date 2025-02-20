package com.ttasjwi.board.system.auth.domain.service

import com.ttasjwi.board.system.auth.domain.model.RefreshTokenHolder
import com.ttasjwi.board.system.member.domain.model.MemberId
import java.time.ZonedDateTime

interface RefreshTokenHolderAppender {
    fun append(memberId: MemberId, refreshTokenHolder: RefreshTokenHolder, currentTime: ZonedDateTime)
    fun removeByMemberId(memberId: MemberId)
}
