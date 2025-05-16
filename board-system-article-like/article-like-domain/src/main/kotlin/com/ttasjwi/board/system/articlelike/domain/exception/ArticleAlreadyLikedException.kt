package com.ttasjwi.board.system.articlelike.domain.exception

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class ArticleAlreadyLikedException(
    articleId: Long,
    userId: Long,
): CustomException(
    status = ErrorStatus.CONFLICT,
    code = "Error.ArticleAlreadyLiked",
    args = listOf(articleId, userId),
    source = "articleId",
    debugMessage = "이 게시글은 이미 좋아요 한 게시글입니다. (articleId=$articleId, userId=$userId)"
)
