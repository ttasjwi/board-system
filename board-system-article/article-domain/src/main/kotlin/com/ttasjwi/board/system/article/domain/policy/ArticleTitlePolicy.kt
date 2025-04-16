package com.ttasjwi.board.system.article.domain.policy

interface ArticleTitlePolicy {
    fun validate(title: String): Result<String>
}
