package com.ttasjwi.board.system.auth.domain.service.fixture

import com.ttasjwi.board.system.auth.domain.model.RefreshTokenHolder
import com.ttasjwi.board.system.auth.domain.service.RefreshTokenHolderAppender
import com.ttasjwi.board.system.auth.domain.service.RefreshTokenHolderFinder
import com.ttasjwi.board.system.member.domain.model.MemberId
import java.time.ZonedDateTime
import java.util.concurrent.ConcurrentHashMap

class RefreshTokenHolderStorageFixture : RefreshTokenHolderAppender, RefreshTokenHolderFinder {

    private val store: MutableMap<MemberId, RefreshTokenHolder> = ConcurrentHashMap()

    override fun append(memberId: MemberId, refreshTokenHolder: RefreshTokenHolder, currentTime: ZonedDateTime) {
        store[memberId] = refreshTokenHolder
    }

    override fun removeByMemberId(memberId: MemberId) {
        store.remove(memberId)
    }

    override fun findByMemberIdOrNull(memberId: MemberId): RefreshTokenHolder? {
        return store[memberId]
    }
}
