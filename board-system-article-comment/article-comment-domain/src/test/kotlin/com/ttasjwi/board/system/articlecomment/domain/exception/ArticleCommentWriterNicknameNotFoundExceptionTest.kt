package com.ttasjwi.board.system.articlecomment.domain.exception

import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-comment-domain] ArticleCommentWriterNicknameNotFoundException")
class ArticleCommentWriterNicknameNotFoundExceptionTest {

    @Test
    @DisplayName("예외 기본값 테스트")
    fun test() {
        val articleCommentWriterId = 12331415L
        val exception = ArticleCommentWriterNicknameNotFoundException(
            articleCommentWriterId = articleCommentWriterId
        )

        assertThat(exception.status).isEqualTo(ErrorStatus.NOT_FOUND)
        assertThat(exception.code).isEqualTo("Error.ArticleCommentWriterNicknameNotFound")
        assertThat(exception.source).isEqualTo("articleCommentWriterId")
        assertThat(exception.args).containsExactly(articleCommentWriterId)
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("댓글 작성을 시도했으나, 사용자 닉네임을 조회하는데 실패했습니다. 사용자가 탈퇴했을 가능성이 있습니다. (commentWriterId = $articleCommentWriterId)")
    }
}
