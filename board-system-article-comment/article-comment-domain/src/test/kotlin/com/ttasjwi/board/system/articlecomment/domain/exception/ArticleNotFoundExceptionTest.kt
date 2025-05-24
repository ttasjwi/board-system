package com.ttasjwi.board.system.articlecomment.domain.exception

import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test


@DisplayName("[article-comment-domain] ArticleNotFoundException")
class ArticleNotFoundExceptionTest {

    @Test
    @DisplayName("예외 기본값 테스트")
    fun test() {
        val articleId = 12455674599L

        val exception = ArticleNotFoundException(
            articleId = articleId,
        )

        assertThat(exception.status).isEqualTo(ErrorStatus.NOT_FOUND)
        assertThat(exception.code).isEqualTo("Error.ArticleNotFound")
        assertThat(exception.source).isEqualTo("articleId")
        assertThat(exception.args).containsExactly("articleId", articleId)
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("조건에 부합하는 게시글을 찾지 못 했습니다. (articleId = $articleId)")
    }
}
