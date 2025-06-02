package com.ttasjwi.board.system.article.domain.exception

import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-domain] ArticleUpdateNotAllowedByWriterMismatchException 테스트")
class ArticleUpdateNotAllowedByWriterMismatchExceptionTest {

    @Test
    @DisplayName("예외 기본값 테스트")
    fun test() {
        val articleId = 12313541L
        val requesterUserId = 1213444L
        val exception = ArticleUpdateNotAllowedByWriterMismatchException(
            articleId = articleId,
            requesterUserId = requesterUserId
        )

        assertThat(exception.status).isEqualTo(ErrorStatus.FORBIDDEN)
        assertThat(exception.code).isEqualTo("Error.ArticleUpdateNotAllowed.ByWriterMismatch")
        assertThat(exception.source).isEqualTo("articleId")
        assertThat(exception.args).containsExactly(articleId, requesterUserId)
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("게시글의 작성자가 아니므로, 게시글을 수정할 수 없습니다. (articleId=$articleId, requesterUserId=$requesterUserId)")
    }
}
