package com.ttasjwi.board.system.article.domain.exception

import com.ttasjwi.board.system.article.domain.policy.ArticleContentPolicyImpl
import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-domain] InvalidArticleContentFormatException 테스트")
class InvalidArticleContentFormatExceptionTest {

    @Test
    @DisplayName("예외 기본값 테스트")
    fun test() {
        val exception = InvalidArticleContentFormatException()

        assertThat(exception.status).isEqualTo(ErrorStatus.BAD_REQUEST)
        assertThat(exception.code).isEqualTo("Error.InvalidArticleContentFormat")
        assertThat(exception.source).isEqualTo("articleContent")
        assertThat(exception.args).containsExactly(ArticleContentPolicyImpl.MAX_LENGTH)
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("게시글 본문의 포맷이 유효하지 않습니다. ${ArticleContentPolicyImpl.MAX_LENGTH}자를 넘길 수 없습니다.")
    }
}
