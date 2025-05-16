package com.ttasjwi.board.system.articlelike.domain.exception

import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-like-domain] ArticleLikeNotAllowedException 테스트")
class ArticleLikeNotAllowedExceptionTest {

    @Test
    @DisplayName("예외 기본값 테스트")
    fun test() {
        val articleId = 12455674599L
        val articleCategoryId = 1213444L
        val exception = ArticleLikeNotAllowedException(articleId = articleId, articleCategoryId = articleCategoryId)

        assertThat(exception.status).isEqualTo(ErrorStatus.FORBIDDEN)
        assertThat(exception.code).isEqualTo("Error.ArticleLikeNotAllowed")
        assertThat(exception.source).isEqualTo("articleId")
        assertThat(exception.args).containsExactly(articleId, articleCategoryId)
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("이 게시글의 카테고리 정책에 의해 좋아요가 불가능합니다. (articleId=$articleId, articleCategoryId=$articleCategoryId)")
    }
}
