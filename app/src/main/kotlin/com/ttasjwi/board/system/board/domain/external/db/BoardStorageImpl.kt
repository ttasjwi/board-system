package com.ttasjwi.board.system.board.domain.external.db

import com.ttasjwi.board.system.board.domain.external.db.jpa.JpaBoard
import com.ttasjwi.board.system.board.domain.external.db.jpa.JpaBoardRepository
import com.ttasjwi.board.system.board.domain.model.Board
import com.ttasjwi.board.system.board.domain.model.BoardId
import com.ttasjwi.board.system.board.domain.model.BoardName
import com.ttasjwi.board.system.board.domain.model.BoardSlug
import com.ttasjwi.board.system.board.domain.service.BoardStorage
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class BoardStorageImpl(
    private val jpaBoardRepository: JpaBoardRepository
) : BoardStorage {

    override fun save(board: Board): Board {
        val jpaModel = JpaBoard.from(board)
        val savedJpaModel = jpaBoardRepository.save(jpaModel)

        if (board.id == null) {
            val id = BoardId.restore(savedJpaModel.id!!)
            board.initId(id)
        }
        return board
    }

    override fun findByIdOrNull(id: BoardId): Board? {
        return jpaBoardRepository.findByIdOrNull(id.value)?.restoreDomain()
    }

    override fun existsByName(name: BoardName): Boolean {
        return jpaBoardRepository.existsByName(name.value)
    }

    override fun existsBySlug(slug: BoardSlug): Boolean {
        return jpaBoardRepository.existsBySlug(slug.value)
    }

    override fun findBySlugOrNull(slug: BoardSlug): Board? {
        return jpaBoardRepository.findBySlugOrNull(slug.value)?.restoreDomain()
    }
}
