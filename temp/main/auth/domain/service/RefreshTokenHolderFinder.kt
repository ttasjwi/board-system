package com.ttasjwi.board.system.auth.domain.service

import com.ttasjwi.board.system.auth.domain.model.RefreshTokenHolder

interface RefreshTokenHolderFinder {
    fun findByMemberIdOrNull(memberId: Long): RefreshTokenHolder?
}
