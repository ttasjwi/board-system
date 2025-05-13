package com.ttasjwi.board.system.articlecomment.domain.exception

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class ParentArticleCommentNotFoundException(
    parentCommentId: Long,
): CustomException(
    status = ErrorStatus.NOT_FOUND,
    code = "Error.ParentArticleCommentNotFound",
    source = "parentCommentId",
    args = listOf(parentCommentId),
    debugMessage = "댓글 작성을 시도했으나, 조건에 부합하는 부모 댓글을 찾지 못 했습니다. (parentCommentId = $parentCommentId)",
)
