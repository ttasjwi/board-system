package com.ttasjwi.board.system.articleview.domain.command

import com.ttasjwi.board.system.common.auth.AuthUser

internal data class ArticleViewCountIncreaseCommand(
    val articleId: Long,
    val user: AuthUser,
)
