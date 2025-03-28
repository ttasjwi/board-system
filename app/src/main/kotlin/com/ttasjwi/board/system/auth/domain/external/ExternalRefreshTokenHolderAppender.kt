package com.ttasjwi.board.system.auth.domain.external

import com.ttasjwi.board.system.auth.domain.model.RefreshTokenHolder
import com.ttasjwi.board.system.member.domain.model.MemberId
import java.time.ZonedDateTime

interface ExternalRefreshTokenHolderAppender {

    fun append(memberId: MemberId, refreshTokenHolder: RefreshTokenHolder, expiresAt: ZonedDateTime)
    fun removeByMemberId(memberId: MemberId)
}
