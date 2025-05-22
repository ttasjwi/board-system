package com.ttasjwi.board.system.articlecomment.infra.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository


@Repository("articleCommentJpaArticleCommentCountRepository")
interface JpaArticleCommentCountRepository : JpaRepository<JpaArticleCommentCount, Long> {

    @Modifying
    @Query(
        """
            INSERT INTO article_comment_counts (article_id, comment_count)
            VALUES (:articleId, 1)
            ON DUPLICATE KEY UPDATE comment_count  = comment_count + 1
        """, nativeQuery = true
    )
    fun increase(articleId: Long)

    @Modifying
    @Query(
        """
            UPDATE article_comment_counts
            SET comment_count = comment_count - 1
            WHERE article_id = :articleId
        """, nativeQuery = true
    )
    fun decrease(articleId: Long)
}
