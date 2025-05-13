package com.ttasjwi.board.system.articlecomment.domain.exception

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class InvalidParentArticleCommentException(
    parentCommentId: Long,
    articleId: Long,
) : CustomException(
    status = ErrorStatus.BAD_REQUEST,
    code = "Error.InvalidParentArticleComment",
    source = "parentCommentId",
    args = listOf(parentCommentId, articleId),
    debugMessage = "댓글 작성을 시도했으나, 부모댓글이 게시글의 댓글이 아닙니다. (parentCommentId = $parentCommentId, articleId= $articleId)",
)
