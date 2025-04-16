package com.ttasjwi.board.system.board.domain.policy.fixture

import com.ttasjwi.board.system.board.domain.policy.ArticleCategorySlugPolicy
import com.ttasjwi.board.system.common.exception.ErrorStatus
import com.ttasjwi.board.system.common.exception.fixture.customExceptionFixture

class ArticleCategorySlugPolicyFixture : ArticleCategorySlugPolicy {

    companion object {
        const val ERROR_SLUG = "!ERRORSLUG"
    }

    override fun validate(slug: String): Result<String> = kotlin.runCatching {
        if (slug == ERROR_SLUG) {
            throw customExceptionFixture(
                status = ErrorStatus.BAD_REQUEST,
                code = "Error.InvalidArticleCategorySlugFormat",
                args = listOf(),
                source = "articleCategorySlug",
                debugMessage = "픽스쳐 예외 : 잘못된 게시글 카테고리 슬러그 포맷"
            )
        }
        slug
    }
}
