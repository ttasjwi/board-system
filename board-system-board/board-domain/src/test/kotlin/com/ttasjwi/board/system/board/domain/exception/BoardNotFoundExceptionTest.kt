package com.ttasjwi.board.system.board.domain.exception

import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[board-domain] BoardNotFoundException 테스트")
class BoardNotFoundExceptionTest {

    @Test
    @DisplayName("예외 값 테스트")
    fun test() {
        val source = "boardId"
        val argument = 12334567899L
        val exception = BoardNotFoundException(source = source, argument = argument)

        assertThat(exception.status).isEqualTo(ErrorStatus.NOT_FOUND)
        assertThat(exception.code).isEqualTo("Error.BoardNotFound")
        assertThat(exception.source).isEqualTo(source)
        assertThat(exception.args).containsExactly(source, argument)
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("조건에 부합하는 게시판을 찾지 못 했습니다. ($source = $argument)")
    }
}
