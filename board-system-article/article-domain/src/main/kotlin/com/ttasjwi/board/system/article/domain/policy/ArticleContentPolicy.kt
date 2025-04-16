package com.ttasjwi.board.system.article.domain.policy

interface ArticleContentPolicy {

    fun validate(content: String): Result<String>
}
