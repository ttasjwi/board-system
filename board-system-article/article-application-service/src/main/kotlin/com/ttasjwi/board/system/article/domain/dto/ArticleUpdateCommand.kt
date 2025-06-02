package com.ttasjwi.board.system.article.domain.dto

import com.ttasjwi.board.system.common.auth.AuthUser
import com.ttasjwi.board.system.common.time.AppDateTime

class ArticleUpdateCommand(
    val articleId: Long,
    val title: String,
    val content: String,
    val authUser: AuthUser,
    val currentTime: AppDateTime
)
