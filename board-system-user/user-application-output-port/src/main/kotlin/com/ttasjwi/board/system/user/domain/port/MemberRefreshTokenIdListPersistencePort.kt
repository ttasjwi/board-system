package com.ttasjwi.board.system.user.domain.port

/**
 * 회원별로 관리되는 리프레시토큰 목록을 관리합니다.
 */
interface MemberRefreshTokenIdListPersistencePort {

    fun add(memberId: Long, refreshTokenId: Long, limit: Long)
    fun findAll(memberId: Long): List<Long>
    fun remove(memberId: Long, refreshTokenId: Long)
    fun exists(memberId: Long, refreshTokenId: Long): Boolean
}
