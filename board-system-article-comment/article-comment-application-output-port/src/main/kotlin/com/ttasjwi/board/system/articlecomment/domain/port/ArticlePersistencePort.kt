package com.ttasjwi.board.system.articlecomment.domain.port

import com.ttasjwi.board.system.articlecomment.domain.model.Article

interface ArticlePersistencePort {
    fun findById(articleId: Long): Article?
    fun existsById(articleId: Long): Boolean
}
