package com.ttasjwi.board.system.articlecomment.domain.exception

import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-comment-domain] ArticleCommentWriteNotAllowedException 테스트")
class ArticleCommentWriteNotAllowedExceptionTest {

    @Test
    @DisplayName("예외 기본값 테스트")
    fun test() {
        val articleId = 12455674599L
        val articleCategoryId = 1213444L
        val exception =
            ArticleCommentWriteNotAllowedException(articleId = articleId, articleCategoryId = articleCategoryId)

        assertThat(exception.status).isEqualTo(ErrorStatus.FORBIDDEN)
        assertThat(exception.code).isEqualTo("Error.ArticleCommentWriteNotAllowed")
        assertThat(exception.source).isEqualTo("articleId")
        assertThat(exception.args).containsExactly(articleId, articleCategoryId)
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("이 게시글의 카테고리 정책에 의해 댓글 작성이 불가능합니다. (articleId=$articleId, articleCategoryId=$articleCategoryId)")
    }
}
