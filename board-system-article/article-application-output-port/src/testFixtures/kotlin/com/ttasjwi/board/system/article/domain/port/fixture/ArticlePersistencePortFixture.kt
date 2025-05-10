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

    override fun findAllPage(boardId: Long, offSet: Long, pageSize: Long): List<Article> {
        return storage.values
            .filter { it.boardId == boardId }
            .sortedByDescending { it.articleId }
            .drop(offSet.toInt())
            .take(pageSize.toInt())
    }

    override fun count(boardId: Long, limit: Long): Long {
        return storage.values
            .filter { it.boardId == boardId }
            .sortedByDescending { it.articleId }
            .take(limit.toInt())
            .count()
            .toLong()
    }

    override fun findAllInfiniteScroll(boardId: Long, limit: Long, lastArticleId: Long?): List<Article> {
        return storage.values
            .filter { it.boardId == boardId }
            .sortedByDescending { it.articleId }
            .filter { if (lastArticleId == null) true else it.articleId < lastArticleId }
            .take(limit.toInt())
    }
}
