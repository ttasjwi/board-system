package com.ttasjwi.board.system.articleread.domain.port.fixture

import com.ttasjwi.board.system.articleread.domain.port.BoardArticleCountStorage

class BoardArticleCountStorageFixture : BoardArticleCountStorage {

    private val storage = mutableMapOf<Long, Long>()

    fun save(boardId: Long, articleCount: Long) {
        storage[boardId] = articleCount
    }

    override fun count(boardId: Long): Long {
        return storage[boardId] ?: return 0L
    }
}
