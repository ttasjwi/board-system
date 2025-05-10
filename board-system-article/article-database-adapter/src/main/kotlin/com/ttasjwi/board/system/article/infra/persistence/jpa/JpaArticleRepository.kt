package com.ttasjwi.board.system.article.infra.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface JpaArticleRepository : JpaRepository<JpaArticle, Long> {

    @Query(
        """
        SELECT a.article_id, a.title, a.content, a.board_id, a.article_category_id, a.writer_id, a.writer_nickname, a.created_at, a.modified_at
        FROM articles AS a
        WHERE a.board_id = :boardId
        ORDER BY a.article_id DESC LIMIT :pageSize
        OFFSET :offset
    """, nativeQuery = true
    )
    fun findAllPage(
        @Param("boardId") boardId: Long,
        @Param("offset") offset: Long,
        @Param("pageSize") pageSize: Long
    ): List<JpaArticle>

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
    )
            : Long

    @Query(
        """
           SELECT a.article_id, a.title, a.content, a.board_id, a.article_category_id, a.writer_id, a.writer_nickname, a.created_at, a.modified_at
           FROM articles AS a
           WHERE a.board_id = :boardId
           ORDER BY a.article_id DESC
           LIMIT :limit
        """, nativeQuery = true
    )
    fun findAllInfiniteScroll(
        @Param("boardId") boardId: Long,
        @Param("limit") limit: Long,
    ): List<JpaArticle>


    @Query(
        """
           SELECT a.article_id, a.title, a.content, a.board_id, a.article_category_id, a.writer_id, a.writer_nickname, a.created_at, a.modified_at
           FROM articles AS a
           WHERE a.board_id = :boardId AND a.article_id < :lastArticleId
           ORDER BY a.article_id DESC
           LIMIT :limit
    """, nativeQuery = true
    )
    fun findAllInfiniteScroll(
        @Param("boardId") boardId: Long,
        @Param("limit") limit: Long,
        @Param("lastArticleId") lastArticleId: Long
    ): List<JpaArticle>
}
