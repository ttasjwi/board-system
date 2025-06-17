package com.ttasjwi.board.system.articleread.infra.persistence

import com.ttasjwi.board.system.articleread.domain.port.BoardArticleCountStorage
import com.ttasjwi.board.system.articleread.infra.persistence.jpa.JpaBoardArticleCountRepository
import org.springframework.stereotype.Component

@Component("articleBoardArticleCountStorageImpl")
class BoardArticleCountStorageImpl(
    private val jpaBoardArticleCountRepository: JpaBoardArticleCountRepository
) : BoardArticleCountStorage {

    override fun count(boardId: Long): Long {
        return jpaBoardArticleCountRepository.count(boardId) ?: 0L
    }
}
