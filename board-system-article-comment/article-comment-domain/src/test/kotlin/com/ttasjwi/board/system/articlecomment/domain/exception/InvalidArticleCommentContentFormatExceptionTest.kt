package com.ttasjwi.board.system.articlecomment.domain.exception

import com.ttasjwi.board.system.articlecomment.domain.policy.ArticleCommentContentPolicyImpl
import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-comment-domain] ArticleCommentContentFormatException 테스트")
class InvalidArticleCommentContentFormatExceptionTest {

    @Test
    @DisplayName("예외 기본값 테스트")
    fun test() {
        val exception = InvalidArticleCommentContentFormatException()

        assertThat(exception.status).isEqualTo(ErrorStatus.BAD_REQUEST)
        assertThat(exception.code).isEqualTo("Error.InvalidArticleCommentContentFormat")
        assertThat(exception.source).isEqualTo("articleCommentContent")
        assertThat(exception.args).containsExactly(ArticleCommentContentPolicyImpl.MAX_LENGTH)
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("게시글 댓글의 포맷이 유효하지 않습니다. ${ArticleCommentContentPolicyImpl.MAX_LENGTH}자를 넘길 수 없습니다.")
    }
}
