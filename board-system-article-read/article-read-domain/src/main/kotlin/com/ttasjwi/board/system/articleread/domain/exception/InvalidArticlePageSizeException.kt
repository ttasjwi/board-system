package com.ttasjwi.board.system.articleread.domain.exception

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class InvalidArticlePageSizeException(
    pageSize: Long,
    minPageSize: Long,
    maxPageSize: Long,
): CustomException(
    status = ErrorStatus.BAD_REQUEST,
    code = "Error.InvalidArticlePageSize",
    args = listOf(pageSize, minPageSize, maxPageSize),
    source = "pageSize",
    debugMessage = "게시글 목록 페이지 크기가 유효하지 않습니다. (요청 크기= $pageSize, 최소 크기=$minPageSize, 최대 크기= $maxPageSize)"
)
