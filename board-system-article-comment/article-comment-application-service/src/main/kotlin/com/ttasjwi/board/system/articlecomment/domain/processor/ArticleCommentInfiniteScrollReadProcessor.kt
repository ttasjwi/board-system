package com.ttasjwi.board.system.articlecomment.domain.processor

import com.ttasjwi.board.system.articlecomment.domain.ArticleCommentInfiniteScrollReadResponse
import com.ttasjwi.board.system.articlecomment.domain.dto.ArticleCommentInfiniteScrollReadQuery
import com.ttasjwi.board.system.articlecomment.domain.model.ArticleComment
import com.ttasjwi.board.system.articlecomment.domain.port.ArticleCommentPersistencePort
import com.ttasjwi.board.system.common.annotation.component.ApplicationProcessor

@ApplicationProcessor
class ArticleCommentInfiniteScrollReadProcessor(
    private val articleCommentPersistencePort: ArticleCommentPersistencePort,
) {

    fun readAllInfiniteScroll(query: ArticleCommentInfiniteScrollReadQuery): ArticleCommentInfiniteScrollReadResponse {
        // 실제 요청 건수보다 1건 더 조회
        val limit = query.pageSize + 1
        val findComments = articleCommentPersistencePort
            .findAllInfiniteScroll(
                articleId = query.articleId,
                limit = limit,
                lastRootParentCommentId = query.lastRootParentCommentId,
                lastCommentId = query.lastCommentId,
            )

        // 실제 limit 건 조회됐다면, 다음 요소가 있음을 의미
        val hasNext = findComments.size == limit.toInt()

        // 다음 요소가 있다면, 사용자에게 실제 내려줄 데이터는 pageSize 까지이므로 잘라냄
        val comments = if (hasNext) {
            findComments.subList(0, query.pageSize.toInt())
        } else {
            findComments
        }

        return ArticleCommentInfiniteScrollReadResponse(
            hasNext = hasNext,
            comments = comments.map { it.toArticleInfiniteScrollItem() }
        )
    }

    private fun ArticleComment.toArticleInfiniteScrollItem(): ArticleCommentInfiniteScrollReadResponse.CommentItem {
        return ArticleCommentInfiniteScrollReadResponse.CommentItem(
            deleteStatus = deleteStatus.name,
            data = if (!this.isDeleted()) {
                ArticleCommentInfiniteScrollReadResponse.CommentItem.Data(
                    articleCommentId = this.articleCommentId.toString(),
                    content = this.content,
                    articleId = this.articleId.toString(),
                    rootParentCommentId = this.rootParentCommentId.toString(),
                    writerId = this.writerId.toString(),
                    writerNickname = this.writerNickname,
                    parentCommentWriterId = this.parentCommentWriterId?.toString(),
                    parentCommentWriterNickname = this.parentCommentWriterNickname,
                    createdAt = this.createdAt.toZonedDateTime(),
                    modifiedAt = this.modifiedAt.toZonedDateTime(),
                )
            } else null
        )
    }
}
