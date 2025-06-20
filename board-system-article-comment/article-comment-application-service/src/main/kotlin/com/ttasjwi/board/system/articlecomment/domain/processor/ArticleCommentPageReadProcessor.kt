package com.ttasjwi.board.system.articlecomment.domain.processor

import com.ttasjwi.board.system.articlecomment.domain.ArticleCommentPageReadResponse
import com.ttasjwi.board.system.articlecomment.domain.dto.ArticleCommentPageReadQuery
import com.ttasjwi.board.system.articlecomment.domain.model.ArticleComment
import com.ttasjwi.board.system.articlecomment.domain.port.ArticleCommentCountPersistencePort
import com.ttasjwi.board.system.articlecomment.domain.port.ArticleCommentPersistencePort
import com.ttasjwi.board.system.common.annotation.component.ApplicationProcessor
import com.ttasjwi.board.system.common.page.PagingInfo
import com.ttasjwi.board.system.common.page.calculateOffset

@ApplicationProcessor
internal class ArticleCommentPageReadProcessor(
    private val articleCommentPersistencePort: ArticleCommentPersistencePort,
    private val articleCommentCountPersistencePort: ArticleCommentCountPersistencePort,
) {

    companion object {
        internal const val ARTICLE_COMMENT_PAGE_GROUP_SIZE = 10L
    }

    fun readAllPage(query: ArticleCommentPageReadQuery): ArticleCommentPageReadResponse {
        val commentCount = readCommentCount(query.articleId)
        val comments = readComments(query)

        val pagingInfo = PagingInfo.from(
            page = query.page,
            pageSize = query.pageSize,
            totalCount = commentCount,
            pageGroupSize = ARTICLE_COMMENT_PAGE_GROUP_SIZE
        )

        return makeResponse(pagingInfo, comments)
    }

    private fun readCommentCount(articleId: Long): Long {
        return articleCommentCountPersistencePort.findByIdOrNull(articleId)?.commentCount ?: 0L
    }

    private fun readComments(query: ArticleCommentPageReadQuery) =
        articleCommentPersistencePort.findAllPage(
            articleId = query.articleId,
            offset = calculateOffset(
                page = query.page,
                pageSize = query.pageSize
            ),
            limit = query.pageSize
        )

    private fun makeResponse(
        pagingInfo: PagingInfo,
        comments: List<ArticleComment>
    ) = ArticleCommentPageReadResponse(
        page = pagingInfo.page,
        pageSize = pagingInfo.pageSize,
        pageGroupSize = pagingInfo.pageGroupSize,
        pageGroupStart = pagingInfo.pageGroupStart,
        pageGroupEnd = pagingInfo.pageGroupEnd,
        hasNextPage = pagingInfo.hasNextPage,
        hasNextPageGroup = pagingInfo.hasNextPageGroup,
        comments = comments.map { it.toCommentItem() }
    )

    private fun ArticleComment.toCommentItem(): ArticleCommentPageReadResponse.CommentItem {
        return ArticleCommentPageReadResponse.CommentItem(
            deleteStatus = this.deleteStatus.name,
            data = if (!this.isDeleted()) {
                ArticleCommentPageReadResponse.CommentItem.Data(
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
