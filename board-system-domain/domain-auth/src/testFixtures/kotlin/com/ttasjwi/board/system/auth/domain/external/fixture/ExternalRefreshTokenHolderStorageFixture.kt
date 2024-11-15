package com.ttasjwi.board.system.auth.domain.external.fixture

import com.ttasjwi.board.system.auth.domain.external.ExternalRefreshTokenHolderAppender
import com.ttasjwi.board.system.auth.domain.external.ExternalRefreshTokenHolderFinder
import com.ttasjwi.board.system.auth.domain.model.RefreshTokenHolder
import com.ttasjwi.board.system.member.domain.model.MemberId
import java.time.ZonedDateTime
import java.util.concurrent.ConcurrentHashMap

class ExternalRefreshTokenHolderStorageFixture : ExternalRefreshTokenHolderAppender, ExternalRefreshTokenHolderFinder {

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
