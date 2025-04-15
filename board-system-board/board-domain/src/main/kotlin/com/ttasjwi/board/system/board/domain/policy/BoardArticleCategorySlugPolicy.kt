package com.ttasjwi.board.system.board.domain.policy

interface BoardArticleCategorySlugPolicy {

    fun validate(slug: String): Result<String>
}

