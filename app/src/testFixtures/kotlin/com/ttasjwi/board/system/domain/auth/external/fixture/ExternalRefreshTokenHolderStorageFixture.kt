package com.ttasjwi.board.system.domain.auth.external.fixture

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.domain.auth.external.ExternalRefreshTokenHolderAppender
import com.ttasjwi.board.system.domain.auth.external.ExternalRefreshTokenHolderFinder
import com.ttasjwi.board.system.domain.auth.model.RefreshTokenHolder
import java.util.concurrent.ConcurrentHashMap

class ExternalRefreshTokenHolderStorageFixture : ExternalRefreshTokenHolderAppender, ExternalRefreshTokenHolderFinder {

    private val store: MutableMap<Long, RefreshTokenHolder> = ConcurrentHashMap()

    override fun append(memberId: Long, refreshTokenHolder: RefreshTokenHolder, expiresAt: AppDateTime) {
        store[memberId] = refreshTokenHolder
    }

    override fun removeByMemberId(memberId: Long) {
        store.remove(memberId)
    }

    override fun findByMemberIdOrNull(memberId: Long): RefreshTokenHolder? {
        return store[memberId]
    }
}
