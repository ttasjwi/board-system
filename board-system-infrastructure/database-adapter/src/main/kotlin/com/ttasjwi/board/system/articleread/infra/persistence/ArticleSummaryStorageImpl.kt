package com.ttasjwi.board.system.articleread.infra.persistence

import com.ttasjwi.board.system.articleread.domain.model.ArticleSummaryQueryModel
import com.ttasjwi.board.system.articleread.domain.port.ArticleSummaryStorage
import com.ttasjwi.board.system.articleread.infra.persistence.jpa.JpaArticleRepository
import org.springframework.stereotype.Component

@Component
class ArticleSummaryStorageImpl(
    private val jpaArticleRepository: JpaArticleRepository,
) : ArticleSummaryStorage {

    override fun findAllPage(boardId: Long, limit: Long, offset: Long): List<ArticleSummaryQueryModel> {
        return jpaArticleRepository.findAllPage(
            boardId = boardId,
            limit = limit,
            offset = offset,
        )
    }

    override fun findAllInfiniteScroll(
        boardId: Long,
        limit: Long,
        lastArticleId: Long?
    ): List<ArticleSummaryQueryModel> {
        return when (lastArticleId) {
            null -> jpaArticleRepository.findAllInfiniteScroll(boardId, limit)
            else -> jpaArticleRepository.findAllInfiniteScroll(boardId, lastArticleId, limit)
        }
    }
}
