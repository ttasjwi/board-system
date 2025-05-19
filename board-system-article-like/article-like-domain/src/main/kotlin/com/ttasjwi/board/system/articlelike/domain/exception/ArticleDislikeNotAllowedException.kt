package com.ttasjwi.board.system.articlelike.domain.exception

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class ArticleDislikeNotAllowedException(
    articleId: Long,
    articleCategoryId: Long,
): CustomException(
    status = ErrorStatus.FORBIDDEN,
    code = "Error.ArticleDislikeNotAllowed",
    source = "articleId",
    args = listOf(articleId, articleCategoryId),
    debugMessage = "이 게시글의 카테고리 정책에 의해 싫어요가 불가능합니다. (articleId=$articleId, articleCategoryId=$articleCategoryId)"
)
