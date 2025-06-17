package com.ttasjwi.board.system.articleread.infra.persistence

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import com.ttasjwi.board.system.articleread.domain.model.ArticleSummaryQueryModel
import com.ttasjwi.board.system.articleread.domain.port.ArticleSummaryStorage
import com.ttasjwi.board.system.articleread.infra.persistence.dto.*
import org.springframework.stereotype.Component
import com.ttasjwi.board.system.articleread.infra.persistence.jpa.QJpaArticle.jpaArticle as article
import com.ttasjwi.board.system.articleread.infra.persistence.jpa.QJpaArticleCategory.jpaArticleCategory as articleCategory
import com.ttasjwi.board.system.articleread.infra.persistence.jpa.QJpaArticleCommentCount.jpaArticleCommentCount as articleCommentCount
import com.ttasjwi.board.system.articleread.infra.persistence.jpa.QJpaArticleDislikeCount.jpaArticleDislikeCount as articleDislikeCount
import com.ttasjwi.board.system.articleread.infra.persistence.jpa.QJpaArticleLikeCount.jpaArticleLikeCount as articleLikeCount
import com.ttasjwi.board.system.articleread.infra.persistence.jpa.QJpaBoard.jpaBoard as board

@Component
class ArticleSummaryStorageImpl(
    private val queryFactory: JPAQueryFactory
) : ArticleSummaryStorage {

    override fun findAllPage(boardId: Long, offSet: Long, limit: Long): List<ArticleSummaryQueryModel> {
        return selectArticleSummaryWithJoins()
            .where(article.boardId.eq(boardId))
            .orderBy(article.articleId.desc())
            .offset(offSet)
            .limit(limit)
            .fetch()
    }

    override fun findAllInfiniteScroll(
        boardId: Long,
        limit: Long,
        lastArticleId: Long?
    ): List<ArticleSummaryQueryModel> {
        return selectArticleSummaryWithJoins()
            .where(article.boardId.eq(boardId).and(lastArticleIdCondition(lastArticleId)))
            .orderBy(article.articleId.desc())
            .limit(limit)
            .fetch()
    }

    private fun selectArticleSummaryWithJoins(): JPAQuery<QueryDslArticleSummaryQueryModel> {
        return queryFactory
            .select(projectArticleSummary())
            .from(article)
            .innerJoin(board).on(board.boardId.eq(article.boardId))
            .innerJoin(articleCategory).on(articleCategory.articleCategoryId.eq(article.articleCategoryId))
            .leftJoin(articleCommentCount).on(articleCommentCount.articleId.eq(article.articleId))
            .leftJoin(articleLikeCount).on(articleLikeCount.articleId.eq(article.articleId))
            .leftJoin(articleDislikeCount).on(articleDislikeCount.articleId.eq(article.articleId))
    }

    private fun projectArticleSummary(): QQueryDslArticleSummaryQueryModel {
        return QQueryDslArticleSummaryQueryModel(
            article.articleId,
            article.title,
            QQueryDslArticleSummaryQueryModel_Board(
                article.boardId,
                board.name,
                board.slug
            ),
            QQueryDslArticleSummaryQueryModel_ArticleCategory(
                article.articleCategoryId,
                articleCategory.name,
                articleCategory.slug,
            ),
            QQueryDslArticleSummaryQueryModel_Writer(
                article.writerId,
                article.writerNickname
            ),
            articleCommentCount.commentCount.coalesce(0L),
            articleLikeCount.likeCount.coalesce(0L),
            articleDislikeCount.dislikeCount.coalesce(0L),
            article.createdAt
        )
    }

    private fun lastArticleIdCondition(lastArticleId: Long?): BooleanExpression? {
        return lastArticleId?.let { article.articleId.lt(it) }
    }
}
