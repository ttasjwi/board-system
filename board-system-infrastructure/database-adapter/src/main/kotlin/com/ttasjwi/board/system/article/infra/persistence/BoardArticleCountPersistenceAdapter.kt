package com.ttasjwi.board.system.article.infra.persistence

import com.ttasjwi.board.system.article.domain.model.BoardArticleCount
import com.ttasjwi.board.system.article.domain.port.BoardArticleCountPersistencePort
import com.ttasjwi.board.system.article.infra.persistence.jpa.JpaBoardArticleCountRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component("articleBoardArticleCountPersistenceAdapter")
class BoardArticleCountPersistenceAdapter(
    private val jpaBoardArticleCountRepository: JpaBoardArticleCountRepository
) : BoardArticleCountPersistencePort {

    override fun increase(boardId: Long) {
        jpaBoardArticleCountRepository.increase(boardId)
    }

    override fun decrease(boardId: Long) {
        jpaBoardArticleCountRepository.decrease(boardId)
    }

    override fun findByIdOrNull(boardId: Long): BoardArticleCount? {
        return jpaBoardArticleCountRepository.findByIdOrNull(boardId)?.restoreDomain()
    }
}
