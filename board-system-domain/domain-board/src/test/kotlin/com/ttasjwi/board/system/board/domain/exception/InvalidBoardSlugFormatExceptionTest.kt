package com.ttasjwi.board.system.board.domain.exception

import com.ttasjwi.board.system.board.domain.model.BoardSlug
import com.ttasjwi.board.system.core.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("InvalidBoardSlugFormatException: 유효하지 않은 게시판 슬러그 형식 예외")
class InvalidBoardSlugFormatExceptionTest {

    @Test
    @DisplayName("예외 기본값 테스트")
    fun test() {
        val exception = InvalidBoardSlugFormatException()

        assertThat(exception.status).isEqualTo(ErrorStatus.BAD_REQUEST)
        assertThat(exception.code).isEqualTo("Error.InvalidBoardSlugFormat")
        assertThat(exception.source).isEqualTo("boardSlug")
        assertThat(exception.args).containsExactly(BoardSlug.MIN_LENGTH, BoardSlug.MAX_LENGTH)
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("게시판 슬러그의 형식이 올바르지 않습니다. 최소 ${BoardSlug.MIN_LENGTH}자, 최대 ${BoardSlug.MAX_LENGTH}자여야 하며 영어 소문자, 숫자만 허용됩니다.")
    }
}
