package com.ttasjwi.board.system.board.domain.service

import com.ttasjwi.board.system.board.domain.model.Board

interface BoardStorage {
    fun save(board: Board): Board
    fun findByIdOrNull(id: Long): Board?
    fun existsByName(name: String): Boolean
    fun existsBySlug(slug: String): Boolean
    fun findBySlugOrNull(slug: String): Board?
}
