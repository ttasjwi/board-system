package com.ttasjwi.board.system.articlelike.domain.dto

import com.ttasjwi.board.system.common.auth.AuthUser
import com.ttasjwi.board.system.common.time.AppDateTime

internal data class ArticleLikeCreateCommand(
    val articleId: Long,
    val likeUser: AuthUser,
    val currentTime: AppDateTime
)
