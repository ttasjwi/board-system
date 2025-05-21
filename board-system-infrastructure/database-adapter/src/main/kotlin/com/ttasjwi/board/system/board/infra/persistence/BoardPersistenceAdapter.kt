package com.ttasjwi.board.system.board.infra.persistence

import com.ttasjwi.board.system.board.domain.model.Board
import com.ttasjwi.board.system.board.domain.port.BoardPersistencePort
import com.ttasjwi.board.system.board.infra.persistence.jpa.JpaBoard
import com.ttasjwi.board.system.board.infra.persistence.jpa.JpaBoardRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class BoardPersistenceAdapter(
    private val jpaBoardRepository: JpaBoardRepository
) : BoardPersistencePort {

    override fun save(board: Board): Board {
        val jpaModel = JpaBoard.from(board)
        jpaBoardRepository.save(jpaModel)
        return board
    }

    override fun findByIdOrNull(id: Long): Board? {
        return jpaBoardRepository.findByIdOrNull(id)?.restoreDomain()
    }

    override fun existsByName(name: String): Boolean {
        return jpaBoardRepository.existsByName(name)
    }

    override fun existsBySlug(slug: String): Boolean {
        return jpaBoardRepository.existsBySlug(slug)
    }

    override fun findBySlugOrNull(slug: String): Board? {
        return jpaBoardRepository.findBySlugOrNull(slug)?.restoreDomain()
    }
}
