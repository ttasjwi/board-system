package com.ttasjwi.board.system.article.domain.exception

import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[article-domain] ArticleWriterNicknameNotFoundException 테스트")
class ArticleWriterNicknameNotFoundExceptionTest {
    @Test
    @DisplayName("예외 기본값 테스트")
    fun test() {
        val source = "articleWriterId"
        val argument = 214315135L

        val exception = ArticleWriterNicknameNotFoundException(
            source = source,
            argument = argument,
        )

        assertThat(exception.status).isEqualTo(ErrorStatus.NOT_FOUND)
        assertThat(exception.code).isEqualTo("Error.ArticleWriterNicknameNotFound")
        assertThat(exception.source).isEqualTo(source)
        assertThat(exception.args).containsExactly(source, argument)
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("게시글 작성을 시도했으나, 사용자 닉네임을 조회하는데 실패했습니다. 사용자가 탈퇴했을 가능성이 있습니다. ($source = $argument)")
    }
}
