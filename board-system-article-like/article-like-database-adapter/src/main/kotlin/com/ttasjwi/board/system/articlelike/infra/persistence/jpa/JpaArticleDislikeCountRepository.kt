package com.ttasjwi.board.system.articlelike.infra.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository("articleLikeJpaArticleDislikeCountRepository")
interface JpaArticleDislikeCountRepository : JpaRepository<JpaArticleDislikeCount, Long> {

    @Modifying
    @Query(
        """
            INSERT INTO article_dislike_counts (article_id, dislike_count)
            VALUES (:articleId, 1)
            ON DUPLICATE KEY UPDATE dislike_count  = dislike_count + 1
        """, nativeQuery = true
    )
    fun increase(articleId: Long)

    @Modifying
    @Query(
        """
            UPDATE article_dislike_counts
            SET dislike_count = dislike_count - 1
            WHERE article_id = :articleId
        """, nativeQuery = true
    )
    fun decrease(articleId: Long)
}
