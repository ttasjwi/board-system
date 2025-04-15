package com.ttasjwi.board.system.article.domain.exception

import com.ttasjwi.board.system.article.domain.policy.ArticleTitlePolicyImpl
import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class InvalidArticleTitleFormatException : CustomException(
    status = ErrorStatus.BAD_REQUEST,
    code = "Error.InvalidArticleTitleFormat",
    source = "articleTitle",
    args = listOf(ArticleTitlePolicyImpl.MAX_LENGTH),
    debugMessage = "게시글 제목의 포맷이 유효하지 않습니다. ${ArticleTitlePolicyImpl.MAX_LENGTH}자를 넘길 수 없고, 공백으로만 구성되선 안 됩니다."
)
