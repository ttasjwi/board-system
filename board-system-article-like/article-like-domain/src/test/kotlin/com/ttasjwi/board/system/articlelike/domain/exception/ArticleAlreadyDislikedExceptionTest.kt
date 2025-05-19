package com.ttasjwi.board.system.articlelike.domain.exception

import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-like-domain] ArticleAlreadyDislikedException 테스트")
class ArticleAlreadyDislikedExceptionTest {

    @Test
    @DisplayName("예외 기본값 테스트")
    fun test() {
        val articleId = 12455674599L
        val userId = 1213444L
        val exception = ArticleAlreadyDislikedException(articleId = articleId, userId = userId)

        assertThat(exception.status).isEqualTo(ErrorStatus.CONFLICT)
        assertThat(exception.code).isEqualTo("Error.ArticleAlreadyDisliked")
        assertThat(exception.source).isEqualTo("articleId")
        assertThat(exception.args).containsExactly(articleId, userId)
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("이 게시글은 이미 싫어요 한 게시글입니다. (articleId=$articleId, userId=$userId)")
    }
}
