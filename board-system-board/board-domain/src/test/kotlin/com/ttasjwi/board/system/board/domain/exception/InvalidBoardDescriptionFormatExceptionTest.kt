package com.ttasjwi.board.system.board.domain.exception

import com.ttasjwi.board.system.board.domain.policy.BoardDescriptionPolicyImpl
import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("InvalidBoardDescriptionFormatException: 유효하지 않은 게시판 설명 형식 예외")
class InvalidBoardDescriptionFormatExceptionTest {

    @Test
    @DisplayName("예외 기본값 테스트")
    fun test() {
        val exception = InvalidBoardDescriptionFormatException()

        assertThat(exception.status).isEqualTo(ErrorStatus.BAD_REQUEST)
        assertThat(exception.code).isEqualTo("Error.InvalidBoardDescriptionFormat")
        assertThat(exception.source).isEqualTo("boardDescription")
        assertThat(exception.args).containsExactly(BoardDescriptionPolicyImpl.MAX_LENGTH)
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("게시판 설명의 형식이 올바르지 않습니다. 최대 ${BoardDescriptionPolicyImpl.MAX_LENGTH}자 까지만 허용됩니다.")
    }
}
