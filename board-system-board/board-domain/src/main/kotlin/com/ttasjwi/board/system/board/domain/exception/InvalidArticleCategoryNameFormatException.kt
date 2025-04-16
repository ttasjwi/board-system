package com.ttasjwi.board.system.board.domain.exception

import com.ttasjwi.board.system.board.domain.policy.ArticleCategoryNamePolicyImpl
import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class InvalidArticleCategoryNameFormatException : CustomException(
    status = ErrorStatus.BAD_REQUEST,
    code = "Error.InvalidArticleCategoryNameFormat",
    args = listOf(ArticleCategoryNamePolicyImpl.MAX_LENGTH),
    source = "articleCategoryName",
    debugMessage = "게시글 카테고리명이 유효하지 않습니다. 게시글 카테고리명은 양 끝 공백이 허용되지 않고, ${ArticleCategoryNamePolicyImpl.MAX_LENGTH} 자를 넘을 수 없습니다."
)
