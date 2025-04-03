package com.ttasjwi.board.system.board.domain.service

import com.ttasjwi.board.system.board.domain.model.Board
import com.ttasjwi.board.system.board.domain.model.BoardSlug

interface BoardStorage {
    fun save(board: Board): Board
    fun findByIdOrNull(id: Long): Board?
    fun existsByName(name: String): Boolean
    fun existsBySlug(slug: BoardSlug): Boolean
    fun findBySlugOrNull(slug: BoardSlug): Board?
}
