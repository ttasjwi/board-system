package com.ttasjwi.board.system.domain.auth.service

import com.ttasjwi.board.system.domain.auth.model.RefreshTokenHolder

interface RefreshTokenHolderFinder {
    fun findByMemberIdOrNull(memberId: Long): RefreshTokenHolder?
}
