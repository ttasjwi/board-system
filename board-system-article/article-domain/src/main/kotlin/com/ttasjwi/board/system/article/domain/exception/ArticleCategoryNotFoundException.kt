package com.ttasjwi.board.system.article.domain.exception

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class ArticleCategoryNotFoundException(
    source: String,
    argument: Any,
) : CustomException(
    status = ErrorStatus.NOT_FOUND,
    code = "Error.ArticleCategoryNotFound",
    args = listOf(source, argument),
    source = source,
    debugMessage = "조건에 부합하는 게시글 카테고리를 찾지 못 했습니다. ($source = $argument)"
)
