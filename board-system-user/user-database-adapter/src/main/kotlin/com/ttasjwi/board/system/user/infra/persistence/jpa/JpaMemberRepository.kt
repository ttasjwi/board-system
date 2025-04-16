package com.ttasjwi.board.system.user.infra.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface JpaMemberRepository : JpaRepository<JpaMember, Long> {

    @Query("""
        SELECT m
        FROM JpaMember m
        WHERE m.email = :email
    """)
    fun findByEmailOrNull(
        @Param("email") email: String): JpaMember?


    @Query("""
        SELECT m.member_id AS memberId, m.role AS role
        FROM members m
        WHERE m.member_id = :memberId
    """, nativeQuery = true)
    fun findAuthMemberProjectionOrNull(@Param("memberId") memberId: Long): AuthMemberProjection?

    fun existsByEmail(email: String): Boolean
    fun existsByUsername(username: String): Boolean
    fun existsByNickname(nickname: String): Boolean
}
