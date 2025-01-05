package com.ttasjwi.board.system.board.application.exception

import com.ttasjwi.board.system.core.exception.CustomException
import com.ttasjwi.board.system.core.exception.ErrorStatus

class DuplicateBoardSlugException(
    boardSlug: String,
) : CustomException(
    status = ErrorStatus.CONFLICT,
    code = "Error.DuplicateBoardSlug",
    args = listOf(boardSlug),
    source = "boardSlug",
    debugMessage = "중복되는 게시판 슬러그가 존재합니다.(boardSlug=${boardSlug})"
)
