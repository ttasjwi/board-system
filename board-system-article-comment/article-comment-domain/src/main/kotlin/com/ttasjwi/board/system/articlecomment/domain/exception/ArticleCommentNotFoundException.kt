package com.ttasjwi.board.system.articlecomment.domain.exception

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class ArticleCommentNotFoundException(
    commentId: Long,
): CustomException(
    status = ErrorStatus.NOT_FOUND,
    code = "Error.ArticleCommentNotFound",
    source = "commentId",
    args = listOf(commentId),
    debugMessage = "댓글이 존재하지 않거나, 삭제됐습니다. (commentId = $commentId)",
)
