package com.ttasjwi.board.system.board.domain.exception

import com.ttasjwi.board.system.board.domain.policy.ArticleCategorySlugPolicyImpl
import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class InvalidArticleCategorySlugFormatException : CustomException(
    status = ErrorStatus.BAD_REQUEST,
    code = "Error.InvalidArticleCategorySlugFormat",
    args = listOf(ArticleCategorySlugPolicyImpl.MIN_LENGTH, ArticleCategorySlugPolicyImpl.MAX_LENGTH),
    source = "articleCategorySlug",
    debugMessage = "게시글 카테고리 슬러그는 ${ArticleCategorySlugPolicyImpl.MIN_LENGTH} 자 이상 " +
            "${ArticleCategorySlugPolicyImpl.MAX_LENGTH} 자 이하의 영어 또는 숫자만 허용됩니다."
)
