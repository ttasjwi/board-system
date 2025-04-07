package com.ttasjwi.board.system.application.board.exception

import com.ttasjwi.board.system.global.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("DuplicateBoardNameException: 게시판 이름 중복 예외")
class DuplicateBoardNameExceptionTest {

    @Test
    @DisplayName("예외 기본값 테스트")
    fun test() {
        val boardName = "고양이"
        val exception = DuplicateBoardNameException(boardName)

        assertThat(exception.status).isEqualTo(ErrorStatus.CONFLICT)
        assertThat(exception.code).isEqualTo("Error.DuplicateBoardName")
        assertThat(exception.source).isEqualTo("boardName")
        assertThat(exception.args).containsExactly(boardName)
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("중복되는 게시판 이름이 존재합니다.(boardName=${boardName})")
    }
}
