package com.ttasjwi.board.system.auth.domain.service

import com.ttasjwi.board.system.auth.domain.model.RefreshTokenHolder
import com.ttasjwi.board.system.common.time.AppDateTime

interface RefreshTokenHolderAppender {
    fun append(memberId: Long, refreshTokenHolder: RefreshTokenHolder, currentTime: AppDateTime)
    fun removeByMemberId(memberId: Long)
}
