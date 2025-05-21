package com.ttasjwi.board.system.articlelike.infra.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository("articleLikeJpaArticleLikeCountRepository")
interface JpaArticleLikeCountRepository : JpaRepository<JpaArticleLikeCount, Long> {

    @Modifying
    @Query(
        """
            INSERT INTO article_like_counts (article_id, like_count)
            VALUES (:articleId, 1)
            ON DUPLICATE KEY UPDATE like_count  = like_count + 1
        """, nativeQuery = true
    )
    fun increase(articleId: Long)

    @Modifying
    @Query(
        """
            UPDATE article_like_counts
            SET like_count = like_count - 1
            WHERE article_id = :articleId
        """, nativeQuery = true
    )
    fun decrease(articleId: Long)
}
