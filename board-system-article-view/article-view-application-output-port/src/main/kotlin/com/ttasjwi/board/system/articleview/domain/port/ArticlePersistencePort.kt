package com.ttasjwi.board.system.articleview.domain.port

interface ArticlePersistencePort {
    fun existsById(articleId: Long): Boolean
}
