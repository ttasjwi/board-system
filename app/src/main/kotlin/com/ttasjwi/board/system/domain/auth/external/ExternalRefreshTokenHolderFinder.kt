package com.ttasjwi.board.system.domain.auth.external

import com.ttasjwi.board.system.domain.auth.model.RefreshTokenHolder

interface ExternalRefreshTokenHolderFinder {
    fun findByMemberIdOrNull(memberId: Long): RefreshTokenHolder?
}
