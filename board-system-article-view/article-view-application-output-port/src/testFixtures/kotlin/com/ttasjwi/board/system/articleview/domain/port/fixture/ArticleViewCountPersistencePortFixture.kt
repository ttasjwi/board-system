package com.ttasjwi.board.system.articleview.domain.port.fixture

import com.ttasjwi.board.system.articleview.domain.model.ArticleViewCount
import com.ttasjwi.board.system.articleview.domain.model.fixture.articleViewCountFixture
import com.ttasjwi.board.system.articleview.domain.port.ArticleViewCountPersistencePort

class ArticleViewCountPersistencePortFixture : ArticleViewCountPersistencePort {

    private val storage = mutableMapOf<Long, Long>()

    override fun increase(articleId: Long) {
        if (!storage.containsKey(articleId)) {
            storage[articleId] = 1
        } else {
            storage[articleId] = storage[articleId]!! + 1
        }
    }

    override fun findByIdOrNull(articleId: Long): ArticleViewCount? {
        val viewCount = storage[articleId] ?: return null
        return articleViewCountFixture(
            articleId = articleId,
            viewCount = viewCount
        )
    }
}
