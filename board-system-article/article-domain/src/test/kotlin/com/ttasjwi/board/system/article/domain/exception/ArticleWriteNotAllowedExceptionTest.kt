package com.ttasjwi.board.system.article.domain.exception

import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-domain] ArticleWriteNotAllowedException 테스트")
class ArticleWriteNotAllowedExceptionTest {

    @Test
    @DisplayName("예외 기본값 테스트")
    fun test() {
        val articleCategoryId = 1213444L
        val exception = ArticleWriteNotAllowedException(articleCategoryId = articleCategoryId)

        Assertions.assertThat(exception.status).isEqualTo(ErrorStatus.FORBIDDEN)
        Assertions.assertThat(exception.code).isEqualTo("Error.ArticleWriteNotAllowed")
        assertThat(exception.source).isEqualTo("articleCategoryId")
        assertThat(exception.args).containsExactly(articleCategoryId)
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        Assertions.assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("게시글 카테고리 정책에 의해 게시글 작성이 불가능합니다. (articleCategoryId=$articleCategoryId)")
    }
}
