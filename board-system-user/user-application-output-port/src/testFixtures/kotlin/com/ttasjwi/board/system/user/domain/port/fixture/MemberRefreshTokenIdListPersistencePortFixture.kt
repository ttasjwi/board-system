package com.ttasjwi.board.system.user.domain.port.fixture

import com.ttasjwi.board.system.user.domain.port.MemberRefreshTokenIdListPersistencePort
import java.util.*

class MemberRefreshTokenIdListPersistencePortFixture : MemberRefreshTokenIdListPersistencePort {

    private val storage = mutableMapOf<Long, SortedSet<Long>>()

    override fun add(memberId: Long, refreshTokenId: Long, limit: Long) {
        if (!storage.containsKey(memberId)) {
            storage[memberId] = sortedSetOf()
        }
        val refreshTokenIds = storage[memberId]!!

        if (refreshTokenIds.size == limit.toInt()) {
            val minRefreshTokenId = refreshTokenIds.min()!!
            refreshTokenIds.remove(minRefreshTokenId)
        }
        refreshTokenIds.add(refreshTokenId)
    }

    override fun findAll(memberId: Long): List<Long> {
        val refreshTokenIds = storage[memberId] ?: return emptyList()
        return refreshTokenIds.toList()
    }

    override fun remove(memberId: Long, refreshTokenId: Long) {
        val refreshTokenIds = storage[memberId] ?: return
        refreshTokenIds.remove(refreshTokenId)
    }

    override fun exists(memberId: Long, refreshTokenId: Long): Boolean {
        if (!storage.containsKey(memberId)) {
            return false
        }
        val refreshTokenIds = storage[memberId]!!
        return refreshTokenIds.contains(refreshTokenId)
    }
}
