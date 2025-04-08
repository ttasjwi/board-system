package com.ttasjwi.board.system.board.domain.exception

import com.ttasjwi.board.system.board.domain.service.impl.BoardNameManagerImpl
import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class InvalidBoardNameFormatException : CustomException(
    status = ErrorStatus.BAD_REQUEST,
    code = "Error.InvalidBoardNameFormat",
    args = listOf(BoardNameManagerImpl.MIN_LENGTH, BoardNameManagerImpl.MAX_LENGTH),
    source = "boardName",
    debugMessage = "게시판 이름의 형식이 올바르지 않습니다. 양 끝 공백 없이 최소 ${BoardNameManagerImpl.MIN_LENGTH}자, 최대 ${BoardNameManagerImpl.MAX_LENGTH}자여야 하며 영어, 숫자, 완성형 한글, 공백, / 만 허용됩니다."
)
