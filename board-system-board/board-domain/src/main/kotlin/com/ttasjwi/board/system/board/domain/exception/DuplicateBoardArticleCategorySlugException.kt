package com.ttasjwi.board.system.board.domain.exception

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class DuplicateBoardArticleCategorySlugException(
    boardArticleCategorySlug: String,
) : CustomException(
    status = ErrorStatus.CONFLICT,
    code = "Error.DuplicateBoardArticleCategorySlug",
    args = listOf(boardArticleCategorySlug),
    source = "boardArticleCategorySlug",
    debugMessage = "게시판 내에서 게시글 카테고리 슬러그가 중복됩니다. (boardArticleCategorySlug = ${boardArticleCategorySlug})"
)
