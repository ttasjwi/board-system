package com.ttasjwi.board.system.articleview.domain.exception

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class ArticleNotFoundException(
    articleId: Long,
) : CustomException(
    status = ErrorStatus.NOT_FOUND,
    code = "Error.ArticleNotFound",
    source = "articleId",
    args = listOf("articleId", articleId),
    debugMessage = "조건에 부합하는 게시글을 찾지 못 했습니다. (articleId = $articleId)",
)
