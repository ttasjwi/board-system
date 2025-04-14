package com.ttasjwi.board.system.board.domain.port

import com.ttasjwi.board.system.board.domain.model.Board

interface BoardPersistencePort {
    fun save(board: Board): Board
    fun findByIdOrNull(id: Long): Board?
    fun existsByName(name: String): Boolean
    fun existsBySlug(slug: String): Boolean
    fun findBySlugOrNull(slug: String): Board?
}
