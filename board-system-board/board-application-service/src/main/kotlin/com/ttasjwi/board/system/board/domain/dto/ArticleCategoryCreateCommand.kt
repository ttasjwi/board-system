package com.ttasjwi.board.system.board.domain.dto

import com.ttasjwi.board.system.common.auth.AuthUser
import com.ttasjwi.board.system.common.time.AppDateTime

data class ArticleCategoryCreateCommand(
    val boardId: Long,
    val creator: AuthUser,
    val name: String,
    val slug: String,
    val allowSelfEditDelete: Boolean,
    val allowLike: Boolean,
    val allowDislike: Boolean,
    val currentTime: AppDateTime
)
