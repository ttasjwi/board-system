package com.ttasjwi.board.system.article.domain.exception

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class InvalidArticlePageSizeException(
    pageSize: Long,
    maxPageSize: Long,
): CustomException(
    status = ErrorStatus.BAD_REQUEST,
    code = "Error.InvalidArticlePageSize",
    args = listOf(pageSize, maxPageSize),
    source = "pageSize",
    debugMessage = "게시글 페이지 조회 시, 페이지 크기가 너무 큽니다. (요청 크기= $pageSize, 최대 크기= $pageSize)"
)
