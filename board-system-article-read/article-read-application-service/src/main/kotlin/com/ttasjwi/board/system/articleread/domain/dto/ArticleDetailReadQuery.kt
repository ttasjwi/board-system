package com.ttasjwi.board.system.articleread.domain.dto

import com.ttasjwi.board.system.common.auth.AuthUser

internal data class ArticleDetailReadQuery(
    val articleId: Long,
    val authUser: AuthUser?,
)
