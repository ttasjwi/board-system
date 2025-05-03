package com.ttasjwi.board.system.article.infra.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository("articleJpaUserRepository")
interface JpaUserRepository : JpaRepository<JpaUser, Long> {

    @Query(
        """
            SELECT u.nickname
            FROM ArticleJpaUser u
            WHERE u.userId = :userId
        """
    )
    fun findNicknameByUserIdOrNull(@Param("userId") writerId: Long): String?
}
