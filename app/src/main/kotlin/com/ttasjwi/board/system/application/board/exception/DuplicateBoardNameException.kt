package com.ttasjwi.board.system.application.board.exception

import com.ttasjwi.board.system.global.exception.CustomException
import com.ttasjwi.board.system.global.exception.ErrorStatus

class DuplicateBoardNameException(
    boardName: String,
) : CustomException(
    status = ErrorStatus.CONFLICT,
    code = "Error.DuplicateBoardName",
    args = listOf(boardName),
    source = "boardName",
    debugMessage = "중복되는 게시판 이름이 존재합니다.(boardName=${boardName})"
)
