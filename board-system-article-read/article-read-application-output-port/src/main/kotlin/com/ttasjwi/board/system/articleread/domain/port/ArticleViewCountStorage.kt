package com.ttasjwi.board.system.articleread.domain.port

interface ArticleViewCountStorage {

    fun readViewCount(articleId: Long): Long
}
