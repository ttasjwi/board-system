package com.ttasjwi.board.system.article.domain.exception

import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-domain] InvalidArticlePageSizeException: 게시글 조회 시, 페이지 크기를 너무 크게 요청했을 때 발생하는 예외")
class InvalidArticlePageSizeExceptionTest {

    @Test
    @DisplayName("예외 테스트")
    fun test() {
        val pageSize = 500L
        val maxPageSize = 10L
        val exception = InvalidArticlePageSizeException(
            pageSize = pageSize,
            maxPageSize = maxPageSize
        )

        assertThat(exception.status).isEqualTo(ErrorStatus.BAD_REQUEST)
        assertThat(exception.code).isEqualTo("Error.InvalidArticlePageSize")
        assertThat(exception.source).isEqualTo("pageSize")
        assertThat(exception.args).containsExactly(pageSize, maxPageSize)
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("게시글 페이지 조회 시, 페이지 크기가 너무 큽니다. (요청 크기= $pageSize, 최대 크기= $pageSize)")
    }
}
