package com.ttasjwi.board.system.user.domain.port

/**
 * 회원별로 관리되는 리프레시토큰 목록을 관리합니다.
 */
interface UserRefreshTokenIdListPersistencePort {

    fun add(userId: Long, refreshTokenId: Long, limit: Long)
    fun findAll(userId: Long): List<Long>
    fun remove(userId: Long, refreshTokenId: Long)
    fun exists(userId: Long, refreshTokenId: Long): Boolean
}
