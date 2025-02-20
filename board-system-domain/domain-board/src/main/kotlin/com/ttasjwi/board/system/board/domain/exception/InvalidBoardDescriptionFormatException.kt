package com.ttasjwi.board.system.board.domain.exception

import com.ttasjwi.board.system.board.domain.model.BoardDescription
import com.ttasjwi.board.system.core.exception.CustomException
import com.ttasjwi.board.system.core.exception.ErrorStatus

class InvalidBoardDescriptionFormatException : CustomException(
    status = ErrorStatus.BAD_REQUEST,
    code = "Error.InvalidBoardDescriptionFormat",
    args = listOf(BoardDescription.MAX_LENGTH),
    source = "boardDescription",
    debugMessage = "게시판 설명의 형식이 올바르지 않습니다. 최대 ${BoardDescription.MAX_LENGTH}자 까지만 허용됩니다."
)
