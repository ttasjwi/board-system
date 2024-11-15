package com.ttasjwi.board.system.auth.domain.external

import com.ttasjwi.board.system.auth.domain.model.RefreshTokenHolder
import com.ttasjwi.board.system.core.annotation.component.AppComponent
import com.ttasjwi.board.system.member.domain.model.MemberId
import java.time.ZonedDateTime
import java.util.concurrent.ConcurrentHashMap

@AppComponent
class ExternalRefreshTokenHolderStorageImpl : ExternalRefreshTokenHolderAppender, ExternalRefreshTokenHolderFinder {

    private val store: MutableMap<MemberId, RefreshTokenHolder> = ConcurrentHashMap()

    override fun append(memberId: MemberId, refreshTokenHolder: RefreshTokenHolder, expiresAt: ZonedDateTime) {
        store[memberId] = refreshTokenHolder
    }

    override fun removeByMemberId(memberId: MemberId) {
        store.remove(memberId)
    }

    override fun findByMemberIdOrNull(memberId: MemberId): RefreshTokenHolder? {
        return store[memberId]
    }

}
