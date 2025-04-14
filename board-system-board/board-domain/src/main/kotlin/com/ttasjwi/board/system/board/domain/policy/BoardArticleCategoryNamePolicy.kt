package com.ttasjwi.board.system.board.domain.policy

interface BoardArticleCategoryNamePolicy {
    fun validate(name: String): Result<String>
}
