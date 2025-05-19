package com.ttasjwi.board.system.articlelike.infra.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository("articleLikeJpaArticleLikeRepository")
interface JpaArticleLikeRepository : JpaRepository<JpaArticleLike, Long> {

    fun findByArticleIdAndUserId(articleId: Long, userId: Long): JpaArticleLike?
    fun existsByArticleIdAndUserId(articleId: Long, userId: Long): Boolean

    @Modifying
    @Query(
        """
            DELETE FROM article_likes
            WHERE article_id = :articleId AND user_id = :userId
        """, nativeQuery = true
    )
    fun deleteByArticleIdAndUserId(@Param("articleId") articleId: Long, @Param("userId") userId: Long)
}
