package com.ttasjwi.board.system.member.domain.port

import com.ttasjwi.board.system.common.time.AppDateTime

interface RefreshTokenIdPersistencePort {

    fun save(memberId: Long, refreshTokenId: Long, expiresAt: AppDateTime)
    fun exists(memberId: Long, refreshTokenId: Long): Boolean
    fun remove(memberId: Long, refreshTokenId: Long)
}
