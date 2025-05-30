package com.ttasjwi.board.system.articleread.domain.exception

import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-read-domain] InvalidArticlePageSizeException: 게시글 목록 조회 시, 페이지 크기 범위가 유효하지 않을 때 발생하는 예외")
class InvalidArticlePageSizeExceptionTest {

    @Test
    @DisplayName("예외 테스트")
    fun test() {
        val pageSize = 500L
        val minPageSize = 1L
        val maxPageSize = 10L
        val exception = InvalidArticlePageSizeException(
            pageSize = pageSize,
            minPageSize = minPageSize,
            maxPageSize = maxPageSize
        )

        assertThat(exception.status).isEqualTo(ErrorStatus.BAD_REQUEST)
        assertThat(exception.code).isEqualTo("Error.InvalidArticlePageSize")
        assertThat(exception.source).isEqualTo("pageSize")
        assertThat(exception.args).containsExactly(pageSize, minPageSize, maxPageSize)
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("게시글 목록 페이지 크기가 유효하지 않습니다. (요청 크기= $pageSize, 최소 크기=$minPageSize, 최대 크기= $maxPageSize)")
    }
}
