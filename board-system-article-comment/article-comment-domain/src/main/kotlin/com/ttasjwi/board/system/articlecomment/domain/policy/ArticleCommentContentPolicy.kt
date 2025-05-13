package com.ttasjwi.board.system.articlecomment.domain.policy

interface ArticleCommentContentPolicy {

    fun validate(content: String): Result<String>
}
