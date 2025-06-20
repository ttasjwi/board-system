package com.ttasjwi.board.system.articlecomment.domain.exception

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class InvalidArticleCommentPageException(
    page: Long,
    minPage: Long,
    maxPage: Long,
) : CustomException(
    status = ErrorStatus.BAD_REQUEST,
    code = "Error.InvalidArticleCommentPage",
    args = listOf(page, minPage, maxPage),
    source = "page",
    debugMessage = "댓글 목록 페이지 번호가 유효하지 않습니다. (요청 페이지 번호= $page, 최소 페이지 번호= $minPage, 최대 페이지 번호= $maxPage)"
)
