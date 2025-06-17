package com.ttasjwi.board.system.articleread.domain.port.fixture

import com.ttasjwi.board.system.articleread.domain.model.ArticleSummaryQueryModel
import com.ttasjwi.board.system.articleread.domain.port.ArticleSummaryStorage

class ArticleSummaryStorageFixture : ArticleSummaryStorage {

    private val storage = mutableMapOf<Long, ArticleSummaryQueryModel>()

    fun save(articleSummaryQueryModel: ArticleSummaryQueryModel) {
        storage[articleSummaryQueryModel.articleId] = articleSummaryQueryModel
    }

    fun findByIdOrNull(articleId: Long): ArticleSummaryQueryModel? {
        return storage[articleId]
    }

    override fun findAllPage(boardId: Long, limit: Long, offset: Long): List<ArticleSummaryQueryModel> {
        return storage.values
            .filter { it.boardId == boardId }
            .sortedByDescending { it.articleId }
            .drop(offset.toInt())
            .take(limit.toInt())
    }

    override fun findAllInfiniteScroll(boardId: Long, limit: Long, lastArticleId: Long?): List<ArticleSummaryQueryModel> {
        return storage.values
            .filter { it.boardId == boardId }
            .sortedByDescending { it.articleId }
            .filter { if (lastArticleId == null) true else it.articleId < lastArticleId }
            .take(limit.toInt())
    }
}
