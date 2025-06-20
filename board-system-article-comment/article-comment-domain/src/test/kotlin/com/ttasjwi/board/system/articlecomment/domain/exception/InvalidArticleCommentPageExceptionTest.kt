package com.ttasjwi.board.system.articlecomment.domain.exception

import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-comment-domain] InvalidArticleCommentPageException")
class InvalidArticleCommentPageExceptionTest {

    @Test
    @DisplayName("기본값 테스트")
    fun test() {
        // given
        val page = 55L
        val minPage = 1L
        val maxPage = 100L

        // when
        val exception = InvalidArticleCommentPageException(
            page = page,
            minPage = minPage,
            maxPage = maxPage
        )

        // then
        assertThat(exception.status).isEqualTo(ErrorStatus.BAD_REQUEST)
        assertThat(exception.code).isEqualTo("Error.InvalidArticleCommentPage")
        assertThat(exception.args).containsExactly(page, minPage, maxPage)
        assertThat(exception.source).isEqualTo("page")
        assertThat(exception.message).isEqualTo("댓글 목록 페이지 번호가 유효하지 않습니다. (요청 페이지 번호= $page, 최소 페이지 번호= $minPage, 최대 페이지 번호= $maxPage)")
    }
}
