package com.ttasjwi.board.system.articleread.domain.exception

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class InvalidArticlePageException(
    page: Long,
    minPage: Long,
    maxPage: Long,
): CustomException(
    status = ErrorStatus.BAD_REQUEST,
    code = "Error.InvalidArticlePage",
    args = listOf(page, minPage, maxPage),
    source = "page",
    debugMessage = "게시글 목록 페이지 번호가 유효하지 않습니다. (요청 페이지 번호= $page, 최소 페이지 번호=$minPage, 최대 페이지 번호= $maxPage)"
)
