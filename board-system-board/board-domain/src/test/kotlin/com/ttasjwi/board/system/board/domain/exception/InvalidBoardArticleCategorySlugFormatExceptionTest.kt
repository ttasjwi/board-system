package com.ttasjwi.board.system.board.domain.exception

import com.ttasjwi.board.system.board.domain.policy.BoardArticleCategorySlugPolicyImpl
import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class InvalidBoardArticleCategorySlugFormatExceptionTest {

    @Test
    @DisplayName("예외 기본값 테스트")
    fun test() {
        val exception = InvalidBoardArticleCategorySlugFormatException()

        assertThat(exception.status).isEqualTo(ErrorStatus.BAD_REQUEST)
        assertThat(exception.code).isEqualTo("Error.InvalidBoardArticleCategorySlugFormat")
        assertThat(exception.source).isEqualTo("boardArticleCategorySlug")
        assertThat(exception.args).containsExactly(
            BoardArticleCategorySlugPolicyImpl.MIN_LENGTH,
            BoardArticleCategorySlugPolicyImpl.MAX_LENGTH
        )
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo(
            "게시글 카테고리 슬러그는 ${BoardArticleCategorySlugPolicyImpl.MIN_LENGTH} 자 이상 " +
                    "${BoardArticleCategorySlugPolicyImpl.MAX_LENGTH} 자 이하의 영어 또는 숫자만 허용됩니다."
        )
    }
}

