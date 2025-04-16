package com.ttasjwi.board.system.board.domain.policy

interface ArticleCategorySlugPolicy {

    fun validate(slug: String): Result<String>
}

