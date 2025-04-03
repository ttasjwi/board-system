package com.ttasjwi.board.system.board.domain.external.db

import com.ttasjwi.board.system.board.domain.external.db.jpa.JpaBoard
import com.ttasjwi.board.system.board.domain.external.db.jpa.JpaBoardRepository
import com.ttasjwi.board.system.board.domain.model.Board
import com.ttasjwi.board.system.board.domain.service.BoardStorage
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class BoardStorageImpl(
    private val jpaBoardRepository: JpaBoardRepository
) : BoardStorage {

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
