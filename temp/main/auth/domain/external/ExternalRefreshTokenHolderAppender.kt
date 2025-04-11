package com.ttasjwi.board.system.auth.domain.external

import com.ttasjwi.board.system.auth.domain.model.RefreshTokenHolder
import com.ttasjwi.board.system.common.time.AppDateTime

interface ExternalRefreshTokenHolderAppender {

    fun append(memberId: Long, refreshTokenHolder: RefreshTokenHolder, expiresAt: AppDateTime)
    fun removeByMemberId(memberId: Long)
}
