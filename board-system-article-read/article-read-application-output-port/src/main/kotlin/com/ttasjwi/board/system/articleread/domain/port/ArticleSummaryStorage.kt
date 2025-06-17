package com.ttasjwi.board.system.articleread.domain.port

import com.ttasjwi.board.system.articleread.domain.model.ArticleSummaryQueryModel

interface ArticleSummaryStorage {
    fun findAllPage(boardId: Long, offSet: Long, limit: Long): List<ArticleSummaryQueryModel>
    fun findAllInfiniteScroll(boardId: Long, limit: Long, lastArticleId: Long?): List<ArticleSummaryQueryModel>
}
