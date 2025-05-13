package com.ttasjwi.board.system.articlecomment.infra.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository("articleCommentJpaUserRepository")
interface JpaUserRepository : JpaRepository<JpaUser, Long> {

    @Query(
        """
            SELECT u.nickname
            FROM ArticleCommentJpaUser u
            WHERE u.userId = :userId
        """
    )
    fun findNicknameByUserIdOrNull(@Param("userId") userId: Long): String?
}
