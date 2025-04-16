package com.ttasjwi.board.system.user.domain.port

import com.ttasjwi.board.system.common.time.AppDateTime

interface RefreshTokenIdPersistencePort {

    fun save(userId: Long, refreshTokenId: Long, expiresAt: AppDateTime)
    fun exists(userId: Long, refreshTokenId: Long): Boolean
    fun remove(userId: Long, refreshTokenId: Long)
}
