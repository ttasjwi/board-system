package com.ttasjwi.board.system.auth.domain.service.impl

import com.ttasjwi.board.system.auth.domain.model.RefreshTokenHolder
import com.ttasjwi.board.system.auth.domain.service.RefreshTokenHolderFinder
import com.ttasjwi.board.system.core.annotation.component.DomainService
import com.ttasjwi.board.system.member.domain.model.MemberId

@DomainService
internal class RefreshTokenHolderFinderImpl : RefreshTokenHolderFinder {

    override fun findByMemberIdOrNull(memberId: MemberId): RefreshTokenHolder? {
        TODO("Not yet implemented")
    }
}
