package com.ttasjwi.board.system.articlelike.domain.port.fixture

import com.ttasjwi.board.system.articlelike.domain.model.ArticleDislikeCount
import com.ttasjwi.board.system.articlelike.domain.port.ArticleDislikeCountPersistencePort

class ArticleDislikeCountPersistencePortFixture : ArticleDislikeCountPersistencePort {

    private val storage = mutableMapOf<Long, ArticleDislikeCount>()

    override fun save(articleDislikeCount: ArticleDislikeCount): ArticleDislikeCount {
        storage[articleDislikeCount.articleId] = articleDislikeCount
        return articleDislikeCount
    }

    override fun findByIdOrNull(articleId: Long): ArticleDislikeCount? {
        return storage[articleId]
    }
}
