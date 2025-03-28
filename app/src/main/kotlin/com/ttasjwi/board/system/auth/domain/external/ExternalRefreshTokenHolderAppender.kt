package com.ttasjwi.board.system.auth.domain.external

import com.ttasjwi.board.system.auth.domain.model.RefreshTokenHolder
import java.time.ZonedDateTime

interface ExternalRefreshTokenHolderAppender {

    fun append(memberId: Long, refreshTokenHolder: RefreshTokenHolder, expiresAt: ZonedDateTime)
    fun removeByMemberId(memberId: Long)
}
