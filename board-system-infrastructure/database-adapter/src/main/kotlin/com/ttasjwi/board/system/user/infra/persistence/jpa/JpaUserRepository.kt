package com.ttasjwi.board.system.user.infra.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository("userJpaUserRepository")
interface JpaUserRepository : JpaRepository<JpaUser, Long> {

    @Query(
        """
        SELECT u
        FROM UserJpaUser u
        WHERE u.email = :email
    """
    )
    fun findByEmailOrNull(
        @Param("email") email: String): JpaUser?


    @Query("""
        SELECT u.user_id AS userId, u.role AS role
        FROM users u
        WHERE u.user_id = :userId
    """, nativeQuery = true)
    fun findAuthUserProjectionOrNull(@Param("userId") userId: Long): AuthUserProjection?

    fun existsByEmail(email: String): Boolean
    fun existsByUsername(username: String): Boolean
    fun existsByNickname(nickname: String): Boolean
}
