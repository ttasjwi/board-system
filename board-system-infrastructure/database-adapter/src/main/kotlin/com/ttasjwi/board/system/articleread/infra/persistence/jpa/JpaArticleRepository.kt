package com.ttasjwi.board.system.articleread.infra.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository("articleReadJpaArticleRepository")
interface JpaArticleRepository : JpaRepository<JpaArticle, Long> {

    @Query(
        """
        WITH cte AS (
            SELECT a.article_id
            FROM articles AS a
            WHERE a.board_id = :boardId
            LIMIT :limit
        )
        SELECT COUNT(*)
        FROM cte
    """, nativeQuery = true
    )
    fun count(
        @Param("boardId") boardId: Long,
        @Param("limit") limit: Long
    ): Long
}
