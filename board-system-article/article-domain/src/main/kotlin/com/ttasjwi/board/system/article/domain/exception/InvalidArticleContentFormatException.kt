package com.ttasjwi.board.system.article.domain.exception

import com.ttasjwi.board.system.article.domain.policy.ArticleContentPolicyImpl
import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class InvalidArticleContentFormatException: CustomException(
    status = ErrorStatus.BAD_REQUEST,
    code = "Error.InvalidArticleContentFormat",
    source = "articleContent",
    args = listOf(ArticleContentPolicyImpl.MAX_LENGTH),
    debugMessage = "게시글 본문의 포맷이 유효하지 않습니다. ${ArticleContentPolicyImpl.MAX_LENGTH}자를 넘길 수 없습니다."
)
