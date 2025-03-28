package com.ttasjwi.board.system.auth.domain.service.fixture

import com.ttasjwi.board.system.auth.domain.model.RefreshTokenHolder
import com.ttasjwi.board.system.auth.domain.service.RefreshTokenHolderAppender
import com.ttasjwi.board.system.auth.domain.service.RefreshTokenHolderFinder
import java.time.ZonedDateTime
import java.util.concurrent.ConcurrentHashMap

class RefreshTokenHolderStorageFixture : RefreshTokenHolderAppender, RefreshTokenHolderFinder {

    private val store: MutableMap<Long, RefreshTokenHolder> = ConcurrentHashMap()

    override fun append(memberId: Long, refreshTokenHolder: RefreshTokenHolder, currentTime: ZonedDateTime) {
        store[memberId] = refreshTokenHolder
    }

    override fun removeByMemberId(memberId: Long) {
        store.remove(memberId)
    }

    override fun findByMemberIdOrNull(memberId: Long): RefreshTokenHolder? {
        return store[memberId]
    }
}
