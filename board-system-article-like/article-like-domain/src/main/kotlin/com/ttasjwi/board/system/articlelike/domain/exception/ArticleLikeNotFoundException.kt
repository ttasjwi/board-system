package com.ttasjwi.board.system.articlelike.domain.exception

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class ArticleLikeNotFoundException(
    articleId: Long,
    userId: Long,
): CustomException(
    status = ErrorStatus.NOT_FOUND,
    code = "Error.ArticleLikeNotFound",
    source = "articleId",
    args = listOf(articleId, userId),
    debugMessage = "이 게시글에 좋아요한 내역이 없습니다. (articleId=$articleId, userId=$userId)"
)
