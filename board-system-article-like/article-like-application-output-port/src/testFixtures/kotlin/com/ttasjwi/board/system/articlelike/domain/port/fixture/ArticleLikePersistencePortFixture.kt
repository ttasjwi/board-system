package com.ttasjwi.board.system.articlelike.domain.port.fixture

import com.ttasjwi.board.system.articlelike.domain.model.ArticleLike
import com.ttasjwi.board.system.articlelike.domain.port.ArticleLikePersistencePort

class ArticleLikePersistencePortFixture : ArticleLikePersistencePort {

    private val storage = mutableMapOf<Long, ArticleLike>()

    override fun save(articleLike: ArticleLike): ArticleLike {
        storage[articleLike.articleLikeId] = articleLike
        return articleLike
    }

    override fun findByIdOrNullTest(articleLikeId: Long): ArticleLike? {
        return storage[articleLikeId]
    }

    override fun findByArticleIdAndUserIdOrNull(articleId: Long, userId: Long): ArticleLike? {
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
