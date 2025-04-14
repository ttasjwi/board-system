package com.ttasjwi.board.system.board.domain.exception

import com.ttasjwi.board.system.board.domain.policy.BoardSlugPolicyImpl
import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class InvalidBoardSlugFormatException : CustomException(
    status = ErrorStatus.BAD_REQUEST,
    code = "Error.InvalidBoardSlugFormat",
    args = listOf(BoardSlugPolicyImpl.MIN_LENGTH, BoardSlugPolicyImpl.MAX_LENGTH),
    source = "boardSlug",
    debugMessage = "게시판 슬러그의 형식이 올바르지 않습니다. 최소 ${BoardSlugPolicyImpl.MIN_LENGTH}자, 최대 ${BoardSlugPolicyImpl.MAX_LENGTH}자여야 하며 영어 소문자, 숫자만 허용됩니다."
)
