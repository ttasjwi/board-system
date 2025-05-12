package com.ttasjwi.board.system.articlecomment.domain.policy.fixture

import com.ttasjwi.board.system.articlecomment.domain.policy.ArticleCommentContentPolicy
import com.ttasjwi.board.system.common.exception.ErrorStatus
import com.ttasjwi.board.system.common.exception.fixture.customExceptionFixture

class ArticleCommentContentPolicyFixture : ArticleCommentContentPolicy {

    companion object {
        const val ERROR_CONTENT = "ERROR!!!CONTENT"
    }

    override fun validate(content: String): Result<String> = kotlin.runCatching {
        if (content == ERROR_CONTENT) {
            throw customExceptionFixture(
                status = ErrorStatus.BAD_REQUEST,
                code = "Error.InvalidArticleCommentContentFormat",
                source = "articleCommentContent",
                args = listOf(),
                debugMessage = "픽스쳐 예외 : 게시글 댓글의 포맷이 유효하지 않습니다."
            )
        }
        content
    }
}
