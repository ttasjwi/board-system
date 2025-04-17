package com.ttasjwi.board.system.article.infra.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface JpaUserRepository : JpaRepository<JpaUser, Long> {

    @Query(
        """
            SELECT u.nickname
            FROM JpaUser u
            WHERE u.userId = :userId
        """
    )
    fun findNicknameByUserIdOrNull(@Param("userId") writerId: Long): String?
}
