package com.ttasjwi.board.system.articlecomment.domain.port.fixture

import com.ttasjwi.board.system.articlecomment.domain.model.Article
import com.ttasjwi.board.system.articlecomment.domain.port.ArticlePersistencePort

class ArticlePersistencePortFixture : ArticlePersistencePort {

    private val storage = mutableMapOf<Long, Article>()

    fun save(article: Article): Article {
        storage[article.articleId] = article
        return article
    }

    override fun findById(articleId: Long): Article? {
        return storage[articleId]
    }
}
