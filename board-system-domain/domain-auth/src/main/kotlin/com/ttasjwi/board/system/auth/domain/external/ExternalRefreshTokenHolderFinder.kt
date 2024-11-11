package com.ttasjwi.board.system.auth.domain.external

import com.ttasjwi.board.system.auth.domain.model.RefreshTokenHolder
import com.ttasjwi.board.system.member.domain.model.MemberId

interface ExternalRefreshTokenHolderFinder {

    fun findByMemberIdOrNull(memberId: MemberId): RefreshTokenHolder?
}
