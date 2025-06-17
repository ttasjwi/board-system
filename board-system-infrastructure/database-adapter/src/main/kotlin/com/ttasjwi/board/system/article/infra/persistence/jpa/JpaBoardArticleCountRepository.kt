package com.ttasjwi.board.system.article.infra.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository("articleJpaBoardArticleCountRepository")
interface JpaBoardArticleCountRepository : JpaRepository<JpaBoardArticleCount, Long> {

    @Modifying
    @Query(
        """
            INSERT INTO board_article_counts (board_id, article_count)
            VALUES (:boardId, 1)
            ON DUPLICATE KEY UPDATE article_count  = article_count + 1
        """, nativeQuery = true
    )
    fun increase(@Param("boardId") boardId: Long)

    @Modifying
    @Query(
        """
            UPDATE board_article_counts
            SET article_count = article_count - 1
            WHERE board_id = :boardId
        """, nativeQuery = true
    )
    fun decrease(@Param("boardId") boardId: Long)
}
