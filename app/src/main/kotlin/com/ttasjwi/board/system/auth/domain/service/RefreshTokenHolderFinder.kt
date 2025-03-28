package com.ttasjwi.board.system.auth.domain.service

import com.ttasjwi.board.system.auth.domain.model.RefreshTokenHolder
import com.ttasjwi.board.system.member.domain.model.MemberId

interface RefreshTokenHolderFinder {
    fun findByMemberIdOrNull(memberId: MemberId): RefreshTokenHolder?
}
