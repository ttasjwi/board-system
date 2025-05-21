package com.ttasjwi.board.system.articlelike.domain.port.fixture

import com.ttasjwi.board.system.articlelike.domain.model.ArticleLikeCount
import com.ttasjwi.board.system.articlelike.domain.model.fixture.articleLikeCountFixture
import com.ttasjwi.board.system.articlelike.domain.port.ArticleLikeCountPersistencePort

class ArticleLikeCountPersistencePortFixture : ArticleLikeCountPersistencePort {

    private val storage = mutableMapOf<Long, ArticleLikeCount>()

    override fun increase(articleId: Long) {
        if (!storage.containsKey(articleId)) {
            storage[articleId] = ArticleLikeCount(articleId, 1)
        } else {
            val articleLikeCount = storage[articleId]!!
            storage[articleId] = articleLikeCountFixture(articleId, articleLikeCount.likeCount + 1)
        }
    }

    override fun decrease(articleId: Long) {
        // 전제 : articleId 의 articleLikeCount 가 있어야함 (애플리케이션 로직에서 보장)
        val articleLikeCount = storage[articleId]!!
        storage[articleLikeCount.articleId] = articleLikeCountFixture(articleId, articleLikeCount.likeCount - 1)
    }

    override fun findByIdOrNull(articleId: Long): ArticleLikeCount? {
        return storage[articleId]
    }
}
