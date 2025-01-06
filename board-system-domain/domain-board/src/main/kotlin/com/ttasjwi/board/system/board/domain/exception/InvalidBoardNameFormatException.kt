package com.ttasjwi.board.system.board.domain.exception

import com.ttasjwi.board.system.board.domain.model.BoardName
import com.ttasjwi.board.system.core.exception.CustomException
import com.ttasjwi.board.system.core.exception.ErrorStatus

class InvalidBoardNameFormatException : CustomException(
    status = ErrorStatus.BAD_REQUEST,
    code = "Error.InvalidBoardNameFormat",
    args = listOf(BoardName.MIN_LENGTH, BoardName.MAX_LENGTH),
    source = "boardName",
    debugMessage = "게시판 이름의 형식이 올바르지 않습니다. 양 끝 공백 없이 최소 ${BoardName.MIN_LENGTH}자, 최대 ${BoardName.MAX_LENGTH}자여야 하며 영어, 숫자, 완성형 한글, 공백, / 만 허용됩니다."
)
