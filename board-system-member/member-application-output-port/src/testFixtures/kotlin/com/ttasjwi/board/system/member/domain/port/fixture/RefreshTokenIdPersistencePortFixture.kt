package com.ttasjwi.board.system.member.domain.port.fixture

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.member.domain.port.RefreshTokenIdPersistencePort

class RefreshTokenIdPersistencePortFixture : RefreshTokenIdPersistencePort {

    private val storage = mutableSetOf<String>()

    companion object {
        private const val KEY_FORMAT = "test-key::%s::%s"
    }

    override fun save(memberId: Long, refreshTokenId: Long, expiresAt: AppDateTime) {
        val key = generateKey(memberId, refreshTokenId)
        storage.add(key)
    }

    override fun exists(memberId: Long, refreshTokenId: Long): Boolean {
        return storage.contains(generateKey(memberId, refreshTokenId))
    }

    override fun remove(memberId: Long, refreshTokenId: Long) {
        val key = generateKey(memberId, refreshTokenId)
        storage.remove(key)
    }

    private fun generateKey(memberId: Long, refreshTokenId: Long): String {
        return KEY_FORMAT.format(memberId, refreshTokenId)
    }
}
