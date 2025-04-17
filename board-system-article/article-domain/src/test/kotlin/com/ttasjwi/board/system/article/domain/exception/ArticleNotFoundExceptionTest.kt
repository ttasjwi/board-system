package com.ttasjwi.board.system.article.domain.exception

import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("ArticleNotFoundException: 게시글을 찾지 못했을 때 발생하는 예외")
class ArticleNotFoundExceptionTest {
    @Test
    @DisplayName("예외 기본값 테스트")
    fun test() {
        val source = "articleId"
        val argument = 12455674599L

        val exception = ArticleNotFoundException(
            source = source,
            argument = argument,
        )

        assertThat(exception.status).isEqualTo(ErrorStatus.NOT_FOUND)
        assertThat(exception.code).isEqualTo("Error.ArticleNotFound")
        assertThat(exception.source).isEqualTo(source)
        assertThat(exception.args).containsExactly(source, argument)
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("조건에 부합하는 게시글을 찾지 못 했습니다. ($source = $argument)")
    }
}
