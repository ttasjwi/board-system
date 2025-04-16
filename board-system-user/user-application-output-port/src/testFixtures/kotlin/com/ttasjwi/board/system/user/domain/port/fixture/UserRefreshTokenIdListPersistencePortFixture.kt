package com.ttasjwi.board.system.user.domain.port.fixture

import com.ttasjwi.board.system.user.domain.port.UserRefreshTokenIdListPersistencePort
import java.util.*

class UserRefreshTokenIdListPersistencePortFixture : UserRefreshTokenIdListPersistencePort {

    private val storage = mutableMapOf<Long, SortedSet<Long>>()

    override fun add(userId: Long, refreshTokenId: Long, limit: Long) {
        if (!storage.containsKey(userId)) {
            storage[userId] = sortedSetOf()
        }
        val refreshTokenIds = storage[userId]!!

        if (refreshTokenIds.size == limit.toInt()) {
            val minRefreshTokenId = refreshTokenIds.min()!!
            refreshTokenIds.remove(minRefreshTokenId)
        }
        refreshTokenIds.add(refreshTokenId)
    }

    override fun findAll(userId: Long): List<Long> {
        val refreshTokenIds = storage[userId] ?: return emptyList()
        return refreshTokenIds.toList()
    }

    override fun remove(userId: Long, refreshTokenId: Long) {
        val refreshTokenIds = storage[userId] ?: return
        refreshTokenIds.remove(refreshTokenId)
    }

    override fun exists(userId: Long, refreshTokenId: Long): Boolean {
        if (!storage.containsKey(userId)) {
            return false
        }
        val refreshTokenIds = storage[userId]!!
        return refreshTokenIds.contains(refreshTokenId)
    }
}
