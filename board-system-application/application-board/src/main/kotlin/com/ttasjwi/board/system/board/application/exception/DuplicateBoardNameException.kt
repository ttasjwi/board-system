package com.ttasjwi.board.system.board.application.exception

import com.ttasjwi.board.system.core.exception.CustomException
import com.ttasjwi.board.system.core.exception.ErrorStatus

class DuplicateBoardNameException(
    boardName: String,
) : CustomException(
    status = ErrorStatus.CONFLICT,
    code = "Error.DuplicateBoardName",
    args = listOf(boardName),
    source = "boardName",
    debugMessage = "중복되는 게시판 이름이 존재합니다.(boardName=${boardName})"
)
