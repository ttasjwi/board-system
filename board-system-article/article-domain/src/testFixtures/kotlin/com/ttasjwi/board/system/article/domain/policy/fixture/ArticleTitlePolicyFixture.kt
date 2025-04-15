package com.ttasjwi.board.system.article.domain.policy.fixture

import com.ttasjwi.board.system.article.domain.policy.ArticleTitlePolicy
import com.ttasjwi.board.system.common.exception.ErrorStatus
import com.ttasjwi.board.system.common.exception.fixture.customExceptionFixture

class ArticleTitlePolicyFixture : ArticleTitlePolicy {

    companion object {

        const val ERROR_TITLE = "ERROR!!TITLE"
    }

    override fun validate(title: String): Result<String> = kotlin.runCatching {
        if (title == ERROR_TITLE) {
            throw customExceptionFixture(
                status = ErrorStatus.BAD_REQUEST,
                code = "Error.InvalidArticleTitleFormat",
                source = "articleTitle",
                args = listOf(),
                debugMessage = "픽스쳐 예외 : 게시글 제목의 포맷이 유효하지 않습니다."
            )
        }
        title
    }
}
