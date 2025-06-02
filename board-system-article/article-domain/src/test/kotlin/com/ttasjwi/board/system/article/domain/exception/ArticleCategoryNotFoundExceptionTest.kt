package com.ttasjwi.board.system.article.domain.exception

import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-domain] ArticleCategoryNotFoundException 테스트")
class ArticleCategoryNotFoundExceptionTest {
    @Test
    @DisplayName("예외 기본값 테스트")
    fun test() {
        val source = "articleCategoryId"
        val argument = 124556799L

        val exception = ArticleCategoryNotFoundException(
            source = source,
            argument = argument,
        )

        assertThat(exception.status).isEqualTo(ErrorStatus.NOT_FOUND)
        assertThat(exception.code).isEqualTo("Error.ArticleCategoryNotFound")
        assertThat(exception.source).isEqualTo("articleCategoryId")
        assertThat(exception.args).containsExactly(source, argument)
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("조건에 부합하는 게시글 카테고리를 찾지 못 했습니다. ($source = $argument)")
    }
}
