package com.ttasjwi.board.system.article.domain.policy.fixture

import com.ttasjwi.board.system.article.domain.policy.ArticleContentPolicy
import com.ttasjwi.board.system.common.exception.ErrorStatus
import com.ttasjwi.board.system.common.exception.fixture.customExceptionFixture

class ArticleContentPolicyFixture : ArticleContentPolicy {

    companion object {
        const val ERROR_CONTENT = "ERROR!!!CONTENT"
    }

    override fun validate(content: String): Result<String> = kotlin.runCatching {
        if (content == ERROR_CONTENT) {
            throw customExceptionFixture(
                status = ErrorStatus.BAD_REQUEST,
                code = "Error.InvalidArticleContentFormat",
                source = "articleContent",
                args = listOf(),
                debugMessage = "픽스쳐 예외 : 게시글 본문의 포맷이 유효하지 않습니다."
            )
        }
        content
    }
}
