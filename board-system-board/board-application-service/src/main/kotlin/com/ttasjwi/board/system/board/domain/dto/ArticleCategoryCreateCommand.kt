package com.ttasjwi.board.system.board.domain.dto

import com.ttasjwi.board.system.common.auth.AuthMember
import com.ttasjwi.board.system.common.time.AppDateTime

data class ArticleCategoryCreateCommand(
    val boardId: Long,
    val creator: AuthMember,
    val name: String,
    val slug: String,
    val allowSelfDelete: Boolean,
    val allowLike: Boolean,
    val allowDislike: Boolean,
    val currentTime: AppDateTime
)
