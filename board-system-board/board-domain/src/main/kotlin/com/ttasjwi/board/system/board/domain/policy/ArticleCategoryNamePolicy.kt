package com.ttasjwi.board.system.board.domain.policy

interface ArticleCategoryNamePolicy {
    fun validate(name: String): Result<String>
}
