package com.ttasjwi.board.system.articlelike.domain.port.fixture

import com.ttasjwi.board.system.articlelike.domain.model.ArticleLikeCount
import com.ttasjwi.board.system.articlelike.domain.port.ArticleLikeCountPersistencePort

class ArticleLikeCountPersistencePortFixture : ArticleLikeCountPersistencePort {

    private val storage = mutableMapOf<Long, ArticleLikeCount>()

    override fun save(articleLikeCount: ArticleLikeCount): ArticleLikeCount {
        storage[articleLikeCount.articleId] = articleLikeCount
        return articleLikeCount
    }

    override fun findByIdOrNull(articleId: Long): ArticleLikeCount? {
        return storage[articleId]
    }
}
