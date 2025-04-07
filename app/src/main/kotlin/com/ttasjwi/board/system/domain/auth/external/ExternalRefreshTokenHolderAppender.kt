package com.ttasjwi.board.system.domain.auth.external

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.domain.auth.model.RefreshTokenHolder

interface ExternalRefreshTokenHolderAppender {
    fun append(memberId: Long, refreshTokenHolder: RefreshTokenHolder, expiresAt: AppDateTime)
    fun removeByMemberId(memberId: Long)
}
