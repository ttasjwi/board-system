package com.ttasjwi.board.system.board.domain.exception

import com.ttasjwi.board.system.board.domain.policy.BoardArticleCategoryNamePolicyImpl
import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class InvalidBoardArticleCategoryNameFormatException : CustomException(
    status = ErrorStatus.BAD_REQUEST,
    code = "ErrorStatus.InvalidBoardArticleCategoryNameFormat",
    args = listOf(BoardArticleCategoryNamePolicyImpl.MAX_LENGTH),
    source = "boardArticleCategoryName",
    debugMessage = "게시글 카테고리명이 유효하지 않습니다. 게시글 카테고리명은 양 끝 공백이 허용되지 않고, ${BoardArticleCategoryNamePolicyImpl.MAX_LENGTH} 자를 넘을 수 없습니다."
)
