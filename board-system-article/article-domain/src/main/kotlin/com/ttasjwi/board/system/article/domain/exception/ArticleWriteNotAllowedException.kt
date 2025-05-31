package com.ttasjwi.board.system.article.domain.exception

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class ArticleWriteNotAllowedException(
    articleCategoryId: Long,
): CustomException(
    status = ErrorStatus.FORBIDDEN,
    code = "Error.ArticleWriteNotAllowed",
    source = "articleCategoryId",
    args = listOf(articleCategoryId),
    debugMessage = "게시글 카테고리 정책에 의해 게시글 작성이 불가능합니다. (articleCategoryId=$articleCategoryId)"
)
