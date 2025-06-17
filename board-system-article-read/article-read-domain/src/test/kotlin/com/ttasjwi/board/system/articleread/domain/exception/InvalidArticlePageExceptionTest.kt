package com.ttasjwi.board.system.articleread.domain.exception

import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-read-domain] InvalidArticlePageException: 게시글 목록 조회 시, 페이지 번호가 유효하지 않을 때 발생하는 예외")
class InvalidArticlePageExceptionTest {

    @Test
    @DisplayName("예외 테스트")
    fun test() {
        val page = 145L
        val minPage = 1L
        val maxPage = 100L
        val exception = InvalidArticlePageException(
            page = page,
            minPage = minPage,
            maxPage = maxPage
        )
        assertThat(exception.status).isEqualTo(ErrorStatus.BAD_REQUEST)
        assertThat(exception.code).isEqualTo("Error.InvalidArticlePage")
        assertThat(exception.source).isEqualTo("page")
        assertThat(exception.args).containsExactly(page, minPage, maxPage)
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("게시글 목록 페이지 번호가 유효하지 않습니다. (요청 페이지 번호= $page, 최소 페이지 번호=$minPage, 최대 페이지 번호= $maxPage)")
    }
}
