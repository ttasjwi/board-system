package com.ttasjwi.board.system.article.domain.exception

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class ArticleUpdateNotAllowedByCategoryRuleException(
    articleId: Long,
    articleCategoryId: Long,
): CustomException(
    status = ErrorStatus.FORBIDDEN,
    code = "Error.ArticleUpdateNotAllowed.ByCategoryRule",
    args = listOf(articleId, articleCategoryId),
    source = "articleId",
    debugMessage = "게시글의 카테고리 규칙에 따라, 게시글을 수정할 수 없습니다. (articleId=$articleId, articleCategoryId=$articleCategoryId)"
)
