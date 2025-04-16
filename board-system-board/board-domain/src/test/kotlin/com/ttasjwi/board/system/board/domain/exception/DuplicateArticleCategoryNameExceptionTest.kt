package com.ttasjwi.board.system.board.domain.exception

import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("DuplicateArticleCategoryNameException 테스트")
class DuplicateArticleCategoryNameExceptionTest {

    @Test
    @DisplayName("예외 값 테스트")
    fun test() {
        val articleCategoryName = "일반"
        val exception = DuplicateArticleCategoryNameException(
            articleCategoryName = articleCategoryName,
        )

        assertThat(exception.status).isEqualTo(ErrorStatus.CONFLICT)
        assertThat(exception.code).isEqualTo("Error.DuplicateArticleCategoryName")
        assertThat(exception.source).isEqualTo("articleCategoryName")
        assertThat(exception.args).containsExactly(articleCategoryName)
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("게시판 내에서 게시글 카테고리명이 중복됩니다. (articleCategoryName = ${articleCategoryName})")
    }
}
