package com.ttasjwi.board.system.board.domain.service

interface BoardSlugManager {

    fun validate(boardSlug: String): Result<String>
}
