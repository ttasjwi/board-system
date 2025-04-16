package com.ttasjwi.board.system.board.domain.exception

import com.ttasjwi.board.system.board.domain.policy.ArticleCategoryNamePolicyImpl
import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("InvalidArticleCategoryNameFormatException: 게시글 카테고리 이름 포맷이 유효하지 않을 때의 예외")
class InvalidArticleCategoryNameFormatExceptionTest {

    @Test
    @DisplayName("예외 기본값 테스트")
    fun test() {
        val exception = InvalidArticleCategoryNameFormatException()

        assertThat(exception.status).isEqualTo(ErrorStatus.BAD_REQUEST)
        assertThat(exception.code).isEqualTo("Error.InvalidArticleCategoryNameFormat")
        assertThat(exception.source).isEqualTo("articleCategoryName")
        assertThat(exception.args).containsExactly(ArticleCategoryNamePolicyImpl.MAX_LENGTH)
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("게시글 카테고리명이 유효하지 않습니다. 게시글 카테고리명은 양 끝 공백이 허용되지 않고, ${ArticleCategoryNamePolicyImpl.MAX_LENGTH} 자를 넘을 수 없습니다.")
    }
}
