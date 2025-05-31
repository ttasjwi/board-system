package com.ttasjwi.board.system.articleread.infra.persistence

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQueryFactory
import com.ttasjwi.board.system.articleread.domain.model.ArticleDetail
import com.ttasjwi.board.system.articleread.domain.port.ArticleDetailStorage
import com.ttasjwi.board.system.articleread.infra.persistence.dto.QQueryDslArticleDetail
import com.ttasjwi.board.system.articleread.infra.persistence.dto.QQueryDslArticleDetail_ArticleCategory
import com.ttasjwi.board.system.articleread.infra.persistence.dto.QQueryDslArticleDetail_Board
import com.ttasjwi.board.system.articleread.infra.persistence.dto.QQueryDslArticleDetail_Writer
import org.springframework.stereotype.Component
import com.ttasjwi.board.system.articleread.infra.persistence.jpa.QJpaArticle.jpaArticle as article
import com.ttasjwi.board.system.articleread.infra.persistence.jpa.QJpaArticleCategory.jpaArticleCategory as articleCategory
import com.ttasjwi.board.system.articleread.infra.persistence.jpa.QJpaArticleCommentCount.jpaArticleCommentCount as articleCommentCount
import com.ttasjwi.board.system.articleread.infra.persistence.jpa.QJpaArticleDislike.jpaArticleDislike as articleDislike
import com.ttasjwi.board.system.articleread.infra.persistence.jpa.QJpaArticleDislikeCount.jpaArticleDislikeCount as articleDislikeCount
import com.ttasjwi.board.system.articleread.infra.persistence.jpa.QJpaArticleLike.jpaArticleLike as articleLike
import com.ttasjwi.board.system.articleread.infra.persistence.jpa.QJpaArticleLikeCount.jpaArticleLikeCount as articleLikeCount
import com.ttasjwi.board.system.articleread.infra.persistence.jpa.QJpaBoard.jpaBoard as board

@Component("articleReadArticleDetailStorage")
class ArticleDetailStorageImpl(
    private val queryFactory: JPAQueryFactory
) : ArticleDetailStorage {

    override fun read(articleId: Long, userId: Long?): ArticleDetail? {
        return queryFactory
            .select(
                QQueryDslArticleDetail(
                    article.articleId,
                    article.title,
                    article.content,
                    QQueryDslArticleDetail_ArticleCategory(
                        article.articleCategoryId,
                        articleCategory.name,
                        articleCategory.slug,
                        articleCategory.allowSelfEditDelete,
                        articleCategory.allowLike,
                        articleCategory.allowDislike,
                    ),
                    QQueryDslArticleDetail_Board(
                        article.boardId,
                        board.name,
                        board.slug,
                    ),
                    QQueryDslArticleDetail_Writer(
                        article.writerId,
                        article.writerNickname
                    ),
                    articleCommentCount.commentCount.coalesce(0L),
                    articleLiked(articleId, userId),
                    articleLikeCount.likeCount.coalesce(0L),
                    articleDisliked(articleId, userId),
                    articleDislikeCount.dislikeCount.coalesce(0L),
                    article.createdAt,
                    article.modifiedAt
                )
            )
            .from(
                article
            )
            .where(
                article.articleId.eq(articleId)
            )
            .innerJoin(articleCategory).on(articleCategory.articleCategoryId.eq(article.articleCategoryId))
            .innerJoin(board).on(board.boardId.eq(article.boardId))
            .leftJoin(articleCommentCount).on(articleCommentCount.articleId.eq(article.articleId))
            .leftJoin(articleLikeCount).on(articleLikeCount.articleId.eq(article.articleId))
            .leftJoin(articleDislikeCount).on(articleDislikeCount.articleId.eq(article.articleId))
            .fetchOne()
    }

    private fun articleLiked(articleId: Long, userId: Long?): BooleanExpression {
        return if (userId != null) {
            JPAExpressions
                .selectOne()
                .from(articleLike)
                .where(
                    articleLike.articleId.eq(articleId),
                    articleLike.userId.eq(userId),
                )
                .exists()
        } else {
            Expressions.FALSE
        }
    }

    private fun articleDisliked(articleId: Long, userId: Long?): BooleanExpression {
        return if (userId != null) {
            JPAExpressions
                .selectOne()
                .from(articleDislike)
                .where(
                    articleDislike.articleId.eq(articleId),
                    articleDislike.userId.eq(userId),
                )
                .exists()
        } else {
            Expressions.FALSE
        }
    }
}
