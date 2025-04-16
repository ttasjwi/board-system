package com.ttasjwi.board.system.board.domain.policy.fixture

import com.ttasjwi.board.system.board.domain.policy.ArticleCategoryNamePolicy
import com.ttasjwi.board.system.common.exception.ErrorStatus
import com.ttasjwi.board.system.common.exception.fixture.customExceptionFixture

class ArticleCategoryNamePolicyFixture : ArticleCategoryNamePolicy {

    companion object {
        const val ERROR_NAME = "errorNAME!"
    }

    override fun validate(name: String): Result<String> = kotlin.runCatching {
        if (name == ERROR_NAME) {
            throw customExceptionFixture(
                status = ErrorStatus.BAD_REQUEST,
                code = "Error.InvalidArticleCategoryNameFormat",
                args = listOf(),
                source = "articleCategoryName",
                debugMessage = "픽스쳐 예외 : 잘못된 게시글 카테고리 이름 포맷"
            )
        }
        name
    }
}
