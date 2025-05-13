package com.ttasjwi.board.system.articlecomment.domain.exception

import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-comment-domain] ParentArticleCommentNotFoundException")
class ParentArticleCommentNotFoundExceptionTest {

    @Test
    @DisplayName("예외 기본값 테스트")
    fun test() {
        val parentCommentId = 12331415L
        val exception = ParentArticleCommentNotFoundException(
            parentCommentId = parentCommentId
        )

        assertThat(exception.status).isEqualTo(ErrorStatus.NOT_FOUND)
        assertThat(exception.code).isEqualTo("Error.ParentArticleCommentNotFound")
        assertThat(exception.source).isEqualTo("parentCommentId")
        assertThat(exception.args).containsExactly(parentCommentId)
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("댓글 작성을 시도했으나, 조건에 부합하는 부모 댓글을 찾지 못 했습니다. (parentCommentId = $parentCommentId)")
    }
}
