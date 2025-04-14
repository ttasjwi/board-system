package com.ttasjwi.board.system.board.domain.exception

import com.ttasjwi.board.system.board.domain.policy.BoardArticleCategoryNamePolicyImpl
import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class InvalidBoardArticleCategoryNameFormatExceptionTest {

    @Test
    @DisplayName("예외 기본값 테스트")
    fun test() {
        val exception = InvalidBoardArticleCategoryNameFormatException()

        assertThat(exception.status).isEqualTo(ErrorStatus.BAD_REQUEST)
        assertThat(exception.code).isEqualTo("Error.InvalidBoardArticleCategoryNameFormat")
        assertThat(exception.source).isEqualTo("boardArticleCategoryName")
        assertThat(exception.args).containsExactly(BoardArticleCategoryNamePolicyImpl.MAX_LENGTH)
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("게시글 카테고리명이 유효하지 않습니다. 게시글 카테고리명은 양 끝 공백이 허용되지 않고, ${BoardArticleCategoryNamePolicyImpl.MAX_LENGTH} 자를 넘을 수 없습니다.")
    }
}
