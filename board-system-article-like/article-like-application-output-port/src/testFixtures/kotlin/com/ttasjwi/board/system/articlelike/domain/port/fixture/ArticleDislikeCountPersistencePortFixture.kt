package com.ttasjwi.board.system.articlelike.domain.port.fixture

import com.ttasjwi.board.system.articlelike.domain.model.ArticleDislikeCount
import com.ttasjwi.board.system.articlelike.domain.model.fixture.articleDislikeCountFixture
import com.ttasjwi.board.system.articlelike.domain.port.ArticleDislikeCountPersistencePort

class ArticleDislikeCountPersistencePortFixture : ArticleDislikeCountPersistencePort {

    private val storage = mutableMapOf<Long, ArticleDislikeCount>()

    override fun increase(articleId: Long) {
        if (!storage.containsKey(articleId)) {
            storage[articleId] = articleDislikeCountFixture(articleId, 1)
        } else {
            val articleDislikeCount = storage[articleId]!!
            storage[articleId] = articleDislikeCountFixture(articleId, articleDislikeCount.dislikeCount + 1)
        }
    }

    override fun decrease(articleId: Long) {
        // 전제 : articleId 의 articleDislikeCount 가 있어야함 (애플리케이션 로직에서 보장)
        val articleDislikeCount = storage[articleId]!!
        storage[articleDislikeCount.articleId] = articleDislikeCountFixture(articleId, articleDislikeCount.dislikeCount - 1)
    }

    override fun findByIdOrNull(articleId: Long): ArticleDislikeCount? {
        return storage[articleId]
    }
}
