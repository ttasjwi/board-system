package com.ttasjwi.board.system.articleread.domain.processor

import com.ttasjwi.board.system.articleread.domain.ArticleDetailReadResponse
import com.ttasjwi.board.system.articleread.domain.dto.ArticleDetailReadQuery
import com.ttasjwi.board.system.articleread.domain.exception.ArticleNotFoundException
import com.ttasjwi.board.system.articleread.domain.model.ArticleDetail
import com.ttasjwi.board.system.articleread.domain.port.ArticleDetailStorage
import com.ttasjwi.board.system.articleread.domain.port.ArticleViewCountStorage
import com.ttasjwi.board.system.common.annotation.component.ApplicationProcessor

@ApplicationProcessor
internal class ArticleDetailReadProcessor(
    private val articleDetailStorage: ArticleDetailStorage,
    private val articleViewCountStorage: ArticleViewCountStorage,
) {

    fun read(query: ArticleDetailReadQuery): ArticleDetailReadResponse {
        val articleDetail = articleDetailStorage.read(query.articleId, query.authUser?.userId)
            ?: throw ArticleNotFoundException(query.articleId)

        val viewCount = articleViewCountStorage.readViewCount(query.articleId)

        return makeResponse(articleDetail, viewCount)
    }

    private fun makeResponse(articleDetail: ArticleDetail, articleViewCount: Long): ArticleDetailReadResponse {
        return ArticleDetailReadResponse(
            articleId = articleDetail.articleId.toString(),
            title = articleDetail.title,
            content = articleDetail.content,
            board = ArticleDetailReadResponse.Board(
                boardId = articleDetail.board.boardId.toString(),
                name = articleDetail.board.name,
                slug = articleDetail.board.slug,
            ),
            articleCategory = ArticleDetailReadResponse.ArticleCategory(
                articleCategoryId = articleDetail.articleCategory.articleCategoryId.toString(),
                name = articleDetail.articleCategory.name,
                slug = articleDetail.articleCategory.slug,
                allowSelfDelete = articleDetail.articleCategory.allowSelfDelete,
                allowLike = articleDetail.articleCategory.allowLike,
                allowDislike = articleDetail.articleCategory.allowDislike,
            ),
            writer = ArticleDetailReadResponse.Writer(
                writerId = articleDetail.writer.writerId.toString(),
                nickname = articleDetail.writer.nickname,
            ),
            viewCount = articleViewCount,
            commentCount = articleDetail.commentCount,
            liked = articleDetail.liked,
            likeCount = articleDetail.likeCount,
            disliked = articleDetail.disliked,
            dislikeCount = articleDetail.dislikeCount,
            createdAt = articleDetail.createdAt.toZonedDateTime(),
            modifiedAt = articleDetail.modifiedAt.toZonedDateTime()
        )
    }
}
