package com.ttasjwi.board.system.application.board.exception

import com.ttasjwi.board.system.global.exception.CustomException
import com.ttasjwi.board.system.global.exception.ErrorStatus

class DuplicateBoardSlugException(
    boardSlug: String,
) : CustomException(
    status = ErrorStatus.CONFLICT,
    code = "Error.DuplicateBoardSlug",
    args = listOf(boardSlug),
    source = "boardSlug",
    debugMessage = "중복되는 게시판 슬러그가 존재합니다.(boardSlug=${boardSlug})"
)
