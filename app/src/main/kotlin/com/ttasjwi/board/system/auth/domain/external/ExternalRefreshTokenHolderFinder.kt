package com.ttasjwi.board.system.auth.domain.external

import com.ttasjwi.board.system.auth.domain.model.RefreshTokenHolder

interface ExternalRefreshTokenHolderFinder {

    fun findByMemberIdOrNull(memberId: Long): RefreshTokenHolder?
}
