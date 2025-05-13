package com.ttasjwi.board.system.articlecomment.domain.dto

import com.ttasjwi.board.system.common.auth.AuthUser
import com.ttasjwi.board.system.common.time.AppDateTime

internal data class ArticleCommentCreateCommand(
    val content: String,
    val articleId: Long,
    val parentCommentId: Long?,
    val currentTime: AppDateTime,
    val commentWriter: AuthUser,
)
