package com.ttasjwi.board.system.article.domain.exception

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class ArticleUpdateNotAllowedByWriterMismatchException(
    articleId: Long,
    requesterUserId: Long,
): CustomException(
    status = ErrorStatus.FORBIDDEN,
    code = "Error.ArticleUpdateNotAllowed.ByWriterMismatch",
    args = listOf(articleId, requesterUserId),
    source = "articleId",
    debugMessage = "게시글의 작성자가 아니므로, 게시글을 수정할 수 없습니다. (articleId=$articleId, requesterUserId=$requesterUserId)"
)
