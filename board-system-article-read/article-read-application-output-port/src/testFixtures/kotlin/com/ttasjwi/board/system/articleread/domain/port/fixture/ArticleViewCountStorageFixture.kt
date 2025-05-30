package com.ttasjwi.board.system.articleread.domain.port.fixture

import com.ttasjwi.board.system.articleread.domain.port.ArticleViewCountStorage

class ArticleViewCountStorageFixture : ArticleViewCountStorage {

    private val storage = mutableMapOf<Long, Long>()

    fun save(articleId: Long, viewCount: Long) {
        storage[articleId] = viewCount
    }

    override fun readViewCount(articleId: Long): Long {
        return storage[articleId] ?: return 0L
    }

    override fun readViewCounts(articleIds: List<Long>): Map<Long, Long> {
        return articleIds.associateWith { readViewCount(it) }
    }
}
