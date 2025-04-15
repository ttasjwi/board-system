package com.ttasjwi.board.system.board.domain.exception

import com.ttasjwi.board.system.board.domain.policy.BoardArticleCategorySlugPolicyImpl
import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class InvalidBoardArticleCategorySlugFormatException : CustomException(
    status = ErrorStatus.BAD_REQUEST,
    code = "Error.InvalidBoardArticleCategorySlugFormat",
    args = listOf(BoardArticleCategorySlugPolicyImpl.MIN_LENGTH, BoardArticleCategorySlugPolicyImpl.MAX_LENGTH),
    source = "boardArticleCategorySlug",
    debugMessage = "게시글 카테고리 슬러그는 ${BoardArticleCategorySlugPolicyImpl.MIN_LENGTH} 자 이상 " +
            "${BoardArticleCategorySlugPolicyImpl.MAX_LENGTH} 자 이하의 영어 또는 숫자만 허용됩니다."
)
