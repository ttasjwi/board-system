package com.ttasjwi.board.system.article.domain.exception

import com.ttasjwi.board.system.article.domain.policy.ArticleTitlePolicyImpl
import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("InvalidArticleTitleFormatException: 유효하지 않은 게시글 제목 포맷일 때 발생하는 예외")
class InvalidArticleTitleFormatExceptionTest {

    @Test
    @DisplayName("예외 기본값 테스트")
    fun test() {
        val exception = InvalidArticleTitleFormatException()

        assertThat(exception.status).isEqualTo(ErrorStatus.BAD_REQUEST)
        assertThat(exception.code).isEqualTo("Error.InvalidArticleTitleFormat")
        assertThat(exception.source).isEqualTo("articleTitle")
        assertThat(exception.args).containsExactly(ArticleTitlePolicyImpl.MAX_LENGTH)
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("게시글 제목의 포맷이 유효하지 않습니다. ${ArticleTitlePolicyImpl.MAX_LENGTH}자를 넘길 수 없고, 공백으로만 구성되선 안 됩니다.")
    }
}
