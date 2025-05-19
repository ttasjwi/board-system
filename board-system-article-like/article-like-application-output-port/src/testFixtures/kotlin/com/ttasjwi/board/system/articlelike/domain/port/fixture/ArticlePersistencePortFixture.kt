package com.ttasjwi.board.system.articlelike.domain.port.fixture

import com.ttasjwi.board.system.articlelike.domain.model.Article
import com.ttasjwi.board.system.articlelike.domain.port.ArticlePersistencePort

class ArticlePersistencePortFixture : ArticlePersistencePort {

    private val storage = mutableMapOf<Long, Article>()

    fun save(article: Article): Article {
        storage[article.articleId] = article
        return article
    }

    override fun findByIdOrNull(articleId: Long): Article? {
        return storage[articleId]
    }

    override fun existsByArticleId(articleId: Long): Boolean {
        return storage.containsKey(articleId)
    }
}
