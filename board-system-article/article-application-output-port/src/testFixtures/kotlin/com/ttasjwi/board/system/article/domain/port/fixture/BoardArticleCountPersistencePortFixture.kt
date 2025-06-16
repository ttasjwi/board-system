package com.ttasjwi.board.system.article.domain.port.fixture

import com.ttasjwi.board.system.article.domain.model.BoardArticleCount
import com.ttasjwi.board.system.article.domain.model.fixture.boardArticleCountFixture
import com.ttasjwi.board.system.article.domain.port.BoardArticleCountPersistencePort

class BoardArticleCountPersistencePortFixture : BoardArticleCountPersistencePort {

    private val storage = mutableMapOf<Long, BoardArticleCount>()

    override fun increase(boardId: Long) {
        if (!storage.containsKey(boardId)) {
            storage[boardId] = boardArticleCountFixture(boardId = boardId, articleCount = 1)
        } else {
            val boardArticleCount = storage[boardId]!!
            storage[boardId] =
                boardArticleCountFixture(boardId = boardId, articleCount = boardArticleCount.articleCount + 1)
        }
    }

    override fun decrease(boardId: Long) {
        // 전제 : boardId 의 boardArticleCount 가 있어야함 (애플리케이션 로직에서 보장)
        val boardArticleCount = storage[boardId]!!
        storage[boardId] =
            boardArticleCountFixture(boardId = boardId, articleCount = boardArticleCount.articleCount - 1)
    }

    override fun findByIdOrNull(boardId: Long): BoardArticleCount? {
        return storage[boardId]
    }
}
