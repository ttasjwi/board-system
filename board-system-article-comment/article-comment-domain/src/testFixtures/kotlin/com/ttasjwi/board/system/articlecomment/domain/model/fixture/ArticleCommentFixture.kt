package com.ttasjwi.board.system.articlecomment.domain.model.fixture

import com.ttasjwi.board.system.articlecomment.domain.model.ArticleComment
import com.ttasjwi.board.system.articlecomment.domain.model.ArticleCommentDeleteStatus
import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture

fun articleCommentFixture(
    articleCommentId: Long = 1L,
    content: String = "content",
    articleId: Long = 1L,
    rootParentCommentId: Long = 1L,
    writerId: Long = 1L,
    writerNickname: String = "writer-nickname",
    targetCommentWriterId: Long? = null,
    targetCommentWriterNickname: String? = null,
    deleteStatus: ArticleCommentDeleteStatus = ArticleCommentDeleteStatus.NOT_DELETED,
    createdAt: AppDateTime = appDateTimeFixture(),
    modifiedAt: AppDateTime = appDateTimeFixture(),
): ArticleComment {
    return ArticleComment(
        articleCommentId = articleCommentId,
        content = content,
        articleId = articleId,
        rootParentCommentId = rootParentCommentId,
        writerId = writerId,
        writerNickname = writerNickname,
        targetCommentWriterId = targetCommentWriterId,
        targetCommentWriterNickname = targetCommentWriterNickname,
        deleteStatus = deleteStatus,
        createdAt = createdAt,
        modifiedAt = modifiedAt,
    )
}
