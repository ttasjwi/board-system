package com.ttasjwi.board.system.board.domain.exception

import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("DuplicateBoardSlugException: 게시판 슬러그 중복 예외")
class DuplicateBoardSlugExceptionTest {

    @Test
    @DisplayName("예외 기본값 테스트")
    fun test() {
        val boardSlug = "cat"
        val exception = DuplicateBoardSlugException(boardSlug)

        assertThat(exception.status).isEqualTo(ErrorStatus.CONFLICT)
        assertThat(exception.code).isEqualTo("Error.DuplicateBoardSlug")
        assertThat(exception.source).isEqualTo("boardSlug")
        assertThat(exception.args).containsExactly(boardSlug)
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("중복되는 게시판 슬러그가 존재합니다.(boardSlug=${boardSlug})")
    }
}
