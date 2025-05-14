package com.ttasjwi.board.system.articlecomment.domain.exception

import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-comment-domain] InvalidArticleCommentPageSizeException")
class InvalidArticleCommentPageSizeExceptionTest {

    @Test
    @DisplayName("기본값 테스트")
    fun test() {
        // given
        val pageSize = 55L
        val minPageSize = 1L
        val maxPageSize = 50L

        // when
        val exception = InvalidArticleCommentPageSizeException(
            pageSize = pageSize,
            minPageSize = minPageSize,
            maxPageSize = maxPageSize
        )

        // then
        assertThat(exception.status).isEqualTo(ErrorStatus.BAD_REQUEST)
        assertThat(exception.code).isEqualTo("Error.InvalidArticleCommentPageSize")
        assertThat(exception.args).containsExactly(pageSize, minPageSize, maxPageSize)
        assertThat(exception.source).isEqualTo("pageSize")
        assertThat(exception.message).isEqualTo("댓글 목록 페이지 크기가 유효하지 않습니다. (요청 크기= $pageSize, 최소 크기=$minPageSize, 최대 크기= $maxPageSize)")
    }
}
