package com.ttasjwi.board.system.articlecomment.domain.exception

import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-comment-domain] ParentCommentAlreadyDeletedException")
class ParentCommentAlreadyDeletedExceptionTest {

    @Test
    @DisplayName("예외 기본값 테스트")
    fun test() {
        val parentCommentId = 12331415L
        val exception = ParentCommentAlreadyDeletedException(
            parentCommentId = parentCommentId
        )

        assertThat(exception.status).isEqualTo(ErrorStatus.BAD_REQUEST)
        assertThat(exception.code).isEqualTo("Error.ParentCommentAlreadyDeleted")
        assertThat(exception.source).isEqualTo("parentCommentId")
        assertThat(exception.args).containsExactly(parentCommentId)
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("삭제된 댓글에는 대댓글을 달 수 없습니다. (parentCommentId: $parentCommentId)")
    }
}
