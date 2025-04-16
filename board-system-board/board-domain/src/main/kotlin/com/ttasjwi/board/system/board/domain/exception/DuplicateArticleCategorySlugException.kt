package com.ttasjwi.board.system.board.domain.exception

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class DuplicateArticleCategorySlugException(
    articleCategorySlug: String,
) : CustomException(
    status = ErrorStatus.CONFLICT,
    code = "Error.DuplicateArticleCategorySlug",
    args = listOf(articleCategorySlug),
    source = "articleCategorySlug",
    debugMessage = "게시판 내에서 게시글 카테고리 슬러그가 중복됩니다. (articleCategorySlug = ${articleCategorySlug})"
)
