package com.ttasjwi.board.system.auth.domain.external

import com.ttasjwi.board.system.auth.domain.model.RefreshTokenHolder
import com.ttasjwi.board.system.core.annotation.component.AppComponent
import com.ttasjwi.board.system.member.domain.model.MemberId
import java.time.ZonedDateTime

@AppComponent
class ExternalRefreshTokenHolderStorage : ExternalRefreshTokenHolderAppender, ExternalRefreshTokenHolderFinder {

    override fun append(memberId: MemberId, refreshTokenHolder: RefreshTokenHolder, expiresAt: ZonedDateTime) {
        TODO("Not yet implemented")
    }

    override fun findByMemberIdOrNull(memberId: MemberId): RefreshTokenHolder? {
        TODO("Not yet implemented")
    }
}
