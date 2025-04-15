package com.ttasjwi.board.system.board.domain.dto

import com.ttasjwi.board.system.common.auth.AuthMember
import com.ttasjwi.board.system.common.time.AppDateTime

data class BoardArticleCategoryCreateCommand(
    val boardId: Long,
    val creator: AuthMember,
    val boardArticleCategoryName: String,
    val boardArticleCategorySlug: String,
    val allowSelfDelete: Boolean,
    val allowLike: Boolean,
    val allowDislike: Boolean,
    val currentTime: AppDateTime
)
