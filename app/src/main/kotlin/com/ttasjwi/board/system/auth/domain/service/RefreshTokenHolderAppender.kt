package com.ttasjwi.board.system.auth.domain.service

import com.ttasjwi.board.system.auth.domain.model.RefreshTokenHolder
import java.time.ZonedDateTime

interface RefreshTokenHolderAppender {
    fun append(memberId: Long, refreshTokenHolder: RefreshTokenHolder, currentTime: ZonedDateTime)
    fun removeByMemberId(memberId: Long)
}
