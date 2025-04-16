package com.ttasjwi.board.system.article.domain.port

import com.ttasjwi.board.system.article.domain.model.Article

interface ArticlePersistencePort {

    fun save(article: Article): Article
    fun findByIdOrNull(articleId: Long): Article?
}
