package com.ttasjwi.board.system.article.domain.exception

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class ArticleNotFoundException(
    source: String,
    argument: Any,
) : CustomException(
    status = ErrorStatus.NOT_FOUND,
    code = "Error.ArticleNotFound",
    source = source,
    args = listOf(source, argument),
    debugMessage = "조건에 부합하는 게시글을 찾지 못 했습니다. ($source = $argument)",
)
