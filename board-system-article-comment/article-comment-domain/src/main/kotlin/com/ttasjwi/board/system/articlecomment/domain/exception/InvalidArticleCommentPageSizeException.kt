package com.ttasjwi.board.system.articlecomment.domain.exception

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class InvalidArticleCommentPageSizeException(
    pageSize: Long,
    minPageSize: Long,
    maxPageSize: Long,
): CustomException(
    status = ErrorStatus.BAD_REQUEST,
    code = "Error.InvalidArticleCommentPageSize",
    args = listOf(pageSize, minPageSize, maxPageSize),
    source = "pageSize",
    debugMessage = "댓글 목록 페이지 크기가 유효하지 않습니다. (요청 크기= $pageSize, 최소 크기=$minPageSize, 최대 크기= $maxPageSize)"
)
