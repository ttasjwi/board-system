package com.ttasjwi.board.system.articlelike.domain.port.fixture

import com.ttasjwi.board.system.articlelike.domain.model.ArticleDislike
import com.ttasjwi.board.system.articlelike.domain.port.ArticleDislikePersistencePort

class ArticleDislikePersistencePortFixture : ArticleDislikePersistencePort {

    private val storage = mutableMapOf<Long, ArticleDislike>()

    override fun save(articleDislike: ArticleDislike): ArticleDislike {
        storage[articleDislike.articleDislikeId] = articleDislike
        return articleDislike
    }

    override fun findByIdOrNullTest(articleDislikeId: Long): ArticleDislike? {
        return storage[articleDislikeId]
    }

    override fun findByArticleIdAndUserIdOrNull(articleId: Long, userId: Long): ArticleDislike? {
        return storage.values
            .firstOrNull { it.articleId == articleId && it.userId == userId }
    }

    override fun existsByArticleIdAndUserId(articleId: Long, userId: Long): Boolean {
        return storage.values
            .any { it.articleId == articleId && it.userId == userId }
    }

    override fun remove(articleId: Long, userId: Long) {
        storage.values
            .removeIf {
                it.articleId == articleId && it.userId == userId
            }
    }
}
