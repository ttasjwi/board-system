package com.ttasjwi.board.system.article.domain.port.fixture

import com.ttasjwi.board.system.article.domain.model.Article
import com.ttasjwi.board.system.article.domain.port.ArticlePersistencePort

class ArticlePersistencePortFixture : ArticlePersistencePort {

    private val storage = mutableMapOf<Long, Article>()

    override fun save(article: Article): Article {
        storage[article.articleId] = article
        return article
    }

    override fun findByIdOrNull(articleId: Long): Article? {
        return storage[articleId]
    }
}
