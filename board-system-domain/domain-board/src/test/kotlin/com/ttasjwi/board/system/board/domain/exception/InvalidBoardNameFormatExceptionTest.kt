package com.ttasjwi.board.system.board.domain.exception

import com.ttasjwi.board.system.board.domain.model.BoardName
import com.ttasjwi.board.system.core.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("InvalidBoardNameFormatException: 유효하지 않은 게시판 이름 형식 예외")
class InvalidBoardNameFormatExceptionTest {

    @Test
    @DisplayName("예외 기본값 테스트")
    fun test() {
        val exception = InvalidBoardNameFormatException()

        assertThat(exception.status).isEqualTo(ErrorStatus.BAD_REQUEST)
        assertThat(exception.code).isEqualTo("Error.InvalidBoardNameFormat")
        assertThat(exception.source).isEqualTo("boardName")
        assertThat(exception.args).containsExactly(BoardName.MIN_LENGTH, BoardName.MAX_LENGTH)
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("게시판 이름의 형식이 올바르지 않습니다. 양 끝 공백 없이 최소 ${BoardName.MIN_LENGTH}자, 최대 ${BoardName.MAX_LENGTH}자여야 하며 영어, 숫자, 완성형 한글, 공백, / 만 허용됩니다.")
    }
}
