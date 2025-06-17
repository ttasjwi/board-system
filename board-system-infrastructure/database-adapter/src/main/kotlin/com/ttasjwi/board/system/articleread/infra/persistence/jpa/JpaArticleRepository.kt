package com.ttasjwi.board.system.articleread.infra.persistence.jpa

import com.ttasjwi.board.system.articleread.domain.model.ArticleSummaryQueryModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository("articleReadJpaArticleRepository")
interface JpaArticleRepository : JpaRepository<JpaArticle, Long> {
    @Query(
        nativeQuery = true,
        value = """
        WITH cte(article_id) AS (
            SELECT article_id
            FROM articles
            WHERE board_id = :boardId
            ORDER BY article_id DESC
            LIMIT :limit OFFSET :offset
        )
        SELECT 
            a.article_id AS articleId,
            a.title AS title,

            b.board_id AS boardId,
            b.name AS boardName,
            b.slug AS boardSlug,

            ac.article_category_id AS articleCategoryId,
            ac.name AS articleCategoryName,
            ac.slug AS articleCategorySlug,

            a.writer_id AS writerId,
            a.writer_nickname AS writerNickname,

            COALESCE(acc.comment_count, 0) AS commentCount,
            COALESCE(alc.like_count, 0) AS likeCount,
            COALESCE(adc.dislike_count, 0) AS dislikeCount,

            a.created_at AS createdAt
        FROM articles a
        JOIN cte ON a.article_id = cte.article_id
        JOIN boards b ON b.board_id = a.board_id
        JOIN article_categories ac ON ac.article_category_id = a.article_category_id
        LEFT JOIN article_comment_counts acc ON acc.article_id = a.article_id
        LEFT JOIN article_like_counts alc ON alc.article_id = a.article_id
        LEFT JOIN article_dislike_counts adc ON adc.article_id = a.article_id
    """
    )
    fun findAllPage(
        @Param("boardId") boardId: Long,
        @Param("limit") limit: Long,
        @Param("offset") offset: Long
    ): List<ArticleSummaryQueryModel>
}
