package com.ttasjwi.board.system.articlecomment.domain.exception

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class ParentCommentAlreadyDeletedException(
    parentCommentId: Long,
) : CustomException(
    status = ErrorStatus.BAD_REQUEST,
    code = "Error.ParentCommentAlreadyDeleted",
    args = listOf(parentCommentId),
    source = "parentCommentId",
    debugMessage = "삭제된 댓글에는 대댓글을 달 수 없습니다. (parentCommentId: $parentCommentId)"
)
