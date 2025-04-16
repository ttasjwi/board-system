package com.ttasjwi.board.system.user.domain.port.fixture

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.user.domain.port.RefreshTokenIdPersistencePort

class RefreshTokenIdPersistencePortFixture : RefreshTokenIdPersistencePort {

    private val storage = mutableSetOf<String>()

    companion object {
        private const val KEY_FORMAT = "test-key::%s::%s"
    }

    override fun save(userId: Long, refreshTokenId: Long, expiresAt: AppDateTime) {
        val key = generateKey(userId, refreshTokenId)
        storage.add(key)
    }

    override fun exists(userId: Long, refreshTokenId: Long): Boolean {
        return storage.contains(generateKey(userId, refreshTokenId))
    }

    override fun remove(userId: Long, refreshTokenId: Long) {
        val key = generateKey(userId, refreshTokenId)
        storage.remove(key)
    }

    private fun generateKey(userId: Long, refreshTokenId: Long): String {
        return KEY_FORMAT.format(userId, refreshTokenId)
    }
}
