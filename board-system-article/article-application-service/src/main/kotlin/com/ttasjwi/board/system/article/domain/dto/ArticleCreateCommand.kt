package com.ttasjwi.board.system.article.domain.dto

import com.ttasjwi.board.system.common.auth.AuthUser
import com.ttasjwi.board.system.common.time.AppDateTime

internal class ArticleCreateCommand(
    val title: String,
    val content: String,
    val articleCategoryId: Long,
    val writer: AuthUser,
    val currentTime: AppDateTime
)
