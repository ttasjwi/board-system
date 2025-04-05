package com.ttasjwi.board.system.auth.domain.service.fixture

import com.ttasjwi.board.system.auth.domain.model.RefreshTokenHolder
import com.ttasjwi.board.system.auth.domain.service.RefreshTokenHolderAppender
import com.ttasjwi.board.system.auth.domain.service.RefreshTokenHolderFinder
import com.ttasjwi.board.system.global.time.AppDateTime
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
