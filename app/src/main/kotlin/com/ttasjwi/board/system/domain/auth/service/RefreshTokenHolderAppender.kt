package com.ttasjwi.board.system.domain.auth.service

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.domain.auth.model.RefreshTokenHolder

interface RefreshTokenHolderAppender {
    fun append(memberId: Long, refreshTokenHolder: RefreshTokenHolder, currentTime: AppDateTime)
    fun removeByMemberId(memberId: Long)
}
