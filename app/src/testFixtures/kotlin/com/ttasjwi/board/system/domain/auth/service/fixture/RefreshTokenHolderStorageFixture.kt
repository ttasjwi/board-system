package com.ttasjwi.board.system.domain.auth.service.fixture

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.domain.auth.model.RefreshTokenHolder
import com.ttasjwi.board.system.domain.auth.service.RefreshTokenHolderAppender
import com.ttasjwi.board.system.domain.auth.service.RefreshTokenHolderFinder
import java.util.concurrent.ConcurrentHashMap

class RefreshTokenHolderStorageFixture : RefreshTokenHolderAppender, RefreshTokenHolderFinder {

    private val store: MutableMap<Long, RefreshTokenHolder> = ConcurrentHashMap()

    override fun append(memberId: Long, refreshTokenHolder: RefreshTokenHolder, currentTime: AppDateTime) {
        store[memberId] = refreshTokenHolder
    }

    override fun removeByMemberId(memberId: Long) {
        store.remove(memberId)
    }

    override fun findByMemberIdOrNull(memberId: Long): RefreshTokenHolder? {
        return store[memberId]
    }
}
