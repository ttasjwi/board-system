package com.ttasjwi.board.system.auth.domain.external.fixture

import com.ttasjwi.board.system.auth.domain.external.ExternalRefreshTokenHolderAppender
import com.ttasjwi.board.system.auth.domain.external.ExternalRefreshTokenHolderFinder
import com.ttasjwi.board.system.auth.domain.model.RefreshTokenHolder
import java.time.ZonedDateTime
import java.util.concurrent.ConcurrentHashMap

class ExternalRefreshTokenHolderStorageFixture : ExternalRefreshTokenHolderAppender, ExternalRefreshTokenHolderFinder {

    private val store: MutableMap<Long, RefreshTokenHolder> = ConcurrentHashMap()

    override fun append(memberId: Long, refreshTokenHolder: RefreshTokenHolder, expiresAt: ZonedDateTime) {
        store[memberId] = refreshTokenHolder
    }

    override fun removeByMemberId(memberId: Long) {
        store.remove(memberId)
    }

    override fun findByMemberIdOrNull(memberId: Long): RefreshTokenHolder? {
        return store[memberId]
    }
}
