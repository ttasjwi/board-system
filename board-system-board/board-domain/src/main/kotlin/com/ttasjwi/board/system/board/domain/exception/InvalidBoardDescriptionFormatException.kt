package com.ttasjwi.board.system.board.domain.exception

import com.ttasjwi.board.system.board.domain.policy.BoardDescriptionPolicyImpl
import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class InvalidBoardDescriptionFormatException : CustomException(
    status = ErrorStatus.BAD_REQUEST,
    code = "Error.InvalidBoardDescriptionFormat",
    args = listOf(BoardDescriptionPolicyImpl.MAX_LENGTH),
    source = "boardDescription",
    debugMessage = "게시판 설명의 형식이 올바르지 않습니다. 최대 ${BoardDescriptionPolicyImpl.MAX_LENGTH}자 까지만 허용됩니다."
)
