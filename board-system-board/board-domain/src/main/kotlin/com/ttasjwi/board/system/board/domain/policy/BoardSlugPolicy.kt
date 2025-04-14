package com.ttasjwi.board.system.board.domain.policy

interface BoardSlugPolicy {

    fun validate(boardSlug: String): Result<String>
}
