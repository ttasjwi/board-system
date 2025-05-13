package com.ttasjwi.board.system.articlecomment.domain.exception

import com.ttasjwi.board.system.articlecomment.domain.policy.ArticleCommentContentPolicyImpl
import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class InvalidArticleCommentContentFormatException : CustomException(
    status = ErrorStatus.BAD_REQUEST,
    code = "Error.InvalidArticleCommentContentFormat",
    source = "articleCommentContent",
    args = listOf(ArticleCommentContentPolicyImpl.MAX_LENGTH),
    debugMessage = "게시글 댓글의 포맷이 유효하지 않습니다. ${ArticleCommentContentPolicyImpl.MAX_LENGTH}자를 넘길 수 없습니다."
)
