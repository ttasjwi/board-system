package com.ttasjwi.board.system.board.domain.exception

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class DuplicateBoardArticleCategoryNameException(
    boardArticleCategoryName: String,
) : CustomException(
    status = ErrorStatus.CONFLICT,
    code = "Error.DuplicateBoardArticleCategoryName",
    args = listOf(boardArticleCategoryName),
    source = "boardArticleCategoryName",
    debugMessage = "게시판 내에서 게시글 카테고리명이 중복됩니다. (boardArticleCategoryName = ${boardArticleCategoryName})"
)
