package com.ttasjwi.board.system.articlecomment.domain.exception

import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-comment-domain] InvalidParentArticleCommentException")
class InvalidParentArticleCommentExceptionTest {

    @Test
    @DisplayName("예외 기본값 테스트")
    fun test() {
        val parentCommentId = 12331415L
        val articleId = 1313413515135135135L
        val exception = InvalidParentArticleCommentException(
            parentCommentId = parentCommentId,
            articleId= articleId
        )

        assertThat(exception.status).isEqualTo(ErrorStatus.BAD_REQUEST)
        assertThat(exception.code).isEqualTo("Error.InvalidParentArticleComment")
        assertThat(exception.source).isEqualTo("parentCommentId")
        assertThat(exception.args).containsExactly(parentCommentId, articleId)
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("댓글 작성을 시도했으나, 부모댓글이 게시글의 댓글이 아닙니다. (parentCommentId = $parentCommentId, articleId= $articleId)")
    }
}
