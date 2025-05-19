package com.ttasjwi.board.system.articlelike.domain.exception

import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-like-domain] ArticleDislikeNotFoundException 테스트")
class ArticleDislikeNotFoundExceptionTest {

    @Test
    @DisplayName("예외 기본값 테스트")
    fun test() {
        val articleId = 12455674599L
        val userId = 1213444L
        val exception = ArticleDislikeNotFoundException(articleId = articleId, userId = userId)

        assertThat(exception.status).isEqualTo(ErrorStatus.NOT_FOUND)
        assertThat(exception.code).isEqualTo("Error.ArticleDislikeNotFound")
        assertThat(exception.source).isEqualTo("articleId")
        assertThat(exception.args).containsExactly(articleId, userId)
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("이 게시글에 싫어요한 내역이 없습니다. (articleId=$articleId, userId=$userId)")
    }
}
