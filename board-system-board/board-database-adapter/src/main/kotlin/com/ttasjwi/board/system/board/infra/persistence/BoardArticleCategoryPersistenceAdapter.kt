package com.ttasjwi.board.system.board.infra.persistence

import com.ttasjwi.board.system.board.domain.model.BoardArticleCategory
import com.ttasjwi.board.system.board.domain.port.BoardArticleCategoryPersistencePort
import com.ttasjwi.board.system.board.infra.persistence.jpa.JpaBoardArticleCategory
import com.ttasjwi.board.system.board.infra.persistence.jpa.JpaBoardArticleCategoryRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class BoardArticleCategoryPersistenceAdapter(
    private val jpaBoardArticleCategoryRepository: JpaBoardArticleCategoryRepository
) : BoardArticleCategoryPersistencePort {

    override fun save(boardArticleCategory: BoardArticleCategory): BoardArticleCategory {
        val jpaModel = JpaBoardArticleCategory.from(boardArticleCategory)
        jpaBoardArticleCategoryRepository.save(jpaModel)
        return boardArticleCategory
    }

    override fun findByIdOrNull(boardArticleCategoryId: Long): BoardArticleCategory? {
        return jpaBoardArticleCategoryRepository
            .findByIdOrNull(boardArticleCategoryId)
            ?.restoreDomain()
    }

    override fun existsByName(boardId: Long, boardArticleCategoryName: String): Boolean {
        return jpaBoardArticleCategoryRepository.existsByBoardIdAndName(boardId, boardArticleCategoryName)
    }

    override fun existsBySlug(boardId: Long, boardArticleCategorySlug: String): Boolean {
        return jpaBoardArticleCategoryRepository.existsByBoardIdAndSlug(boardId, boardArticleCategorySlug)
    }
}
