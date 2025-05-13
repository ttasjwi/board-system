package com.ttasjwi.board.system.articlecomment.domain.exception

import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-comment-domain] ArticleCommentNotFoundException")
class ArticleCommentNotFoundExceptionTest {

    @Test
    @DisplayName("예외 기본값 테스트")
    fun test() {
        val commentId = 12331415L
        val exception = ArticleCommentNotFoundException(
            commentId = commentId
        )

        assertThat(exception.status).isEqualTo(ErrorStatus.NOT_FOUND)
        assertThat(exception.code).isEqualTo("Error.ArticleCommentNotFound")
        assertThat(exception.source).isEqualTo("commentId")
        assertThat(exception.args).containsExactly(commentId)
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("댓글이 존재하지 않거나, 삭제됐습니다. (commentId = $commentId)")
    }
}
