package com.ttasjwi.board.system.articlelike.domain.dto

import com.ttasjwi.board.system.common.auth.AuthUser
import com.ttasjwi.board.system.common.time.AppDateTime

internal data class ArticleDislikeCreateCommand(
    val articleId: Long,
    val dislikeUser: AuthUser,
    val currentTime: AppDateTime
)
