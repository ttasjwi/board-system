package com.ttasjwi.board.system.articlecomment.infra.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository("articleCommentJpaArticleCommentRepository")
interface JpaArticleCommentRepository : JpaRepository<JpaArticleComment, Long> {

    @Query(
        """
        WITH cte AS (
            SELECT article_comment_id
            FROM article_comments
            WHERE article_id = :articleId
            LIMIT :limit
        )
        SELECT COUNT(*)
        FROM cte
        """, nativeQuery = true
    )
    fun count(
        @Param("articleId")articleId: Long,
        @Param("limit") limit: Long
    ): Long

    @Query(
        """
            WITH cte(article_comment_id) AS (
                SELECT ac.article_comment_id
                FROM article_comments ac
                WHERE ac.article_id = :articleId
                ORDER BY ac.root_parent_comment_id, ac.article_comment_id
                LIMIT :limit OFFSET :offset
            )
            SELECT
                ac.article_comment_id,
                ac.content,
                ac.article_id,
                ac.root_parent_comment_id,
                ac.writer_id,
                ac.writer_nickname,
                ac.parent_comment_writer_id,
                ac.parent_comment_writer_nickname,
                ac.delete_status,
                ac.created_at,
                ac.modified_at
            FROM
                article_comments ac
                    JOIN cte
                        ON cte.article_comment_id = ac.article_comment_id;
        """
        , nativeQuery = true
    )
    fun findAllPage(
        @Param("articleId") articleId: Long,
        @Param("offset") offset: Long,
        @Param("limit") limit: Long
    ): List<JpaArticleComment>

    @Query(
        """
            SELECT 
                article_comment_id,
                content,
                article_id,
                root_parent_comment_id,
                writer_id,
                writer_nickname,
                parent_comment_writer_id,
                parent_comment_writer_nickname,
                delete_status,
                created_at,
                modified_at
            FROM article_comments
            WHERE article_id = :articleId
            ORDER BY root_parent_comment_id , article_comment_id
            LIMIT :limit
        """, nativeQuery = true
    )
    fun findAllInfiniteScroll(
        @Param("articleId") articleId: Long,
        @Param("limit") limit: Long,
    ): List<JpaArticleComment>

    @Query(
        """
            SELECT 
                article_comment_id,
                content,
                article_id,
                root_parent_comment_id,
                writer_id,
                writer_nickname,
                parent_comment_writer_id,
                parent_comment_writer_nickname,
                delete_status,
                created_at,
                modified_at
            FROM article_comments
            WHERE article_id = :articleId AND (
                root_parent_comment_id > :lastRootParentCommentId OR 
                    (root_parent_comment_id = :lastRootParentCommentId AND article_comment_id > :lastCommentId)
            )
            ORDER BY root_parent_comment_id , article_comment_id
            LIMIT :limit
        """, nativeQuery = true
    )
    fun findAllInfiniteScroll(
        @Param("articleId") articleId: Long,
        @Param("limit") limit: Long,
        @Param("lastRootParentCommentId") lastRootParentCommentId: Long,
        @Param("lastCommentId") lastCommentId: Long,
    ): List<JpaArticleComment>
}
