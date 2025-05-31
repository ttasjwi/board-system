package com.ttasjwi.board.system.board.domain.exception

import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[board-domain] DuplicateArticleCategorySlugException 테스트")
class DuplicateArticleCategorySlugExceptionTest {

    @Test
    @DisplayName("예외 값 테스트")
    fun test() {
        val articleCategorySlug = "general"
        val exception = DuplicateArticleCategorySlugException(
            articleCategorySlug = articleCategorySlug,
        )

        assertThat(exception.status).isEqualTo(ErrorStatus.CONFLICT)
        assertThat(exception.code).isEqualTo("Error.DuplicateArticleCategorySlug")
        assertThat(exception.source).isEqualTo("articleCategorySlug")
        assertThat(exception.args).containsExactly(articleCategorySlug)
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("게시판 내에서 게시글 카테고리 슬러그가 중복됩니다. (articleCategorySlug = ${articleCategorySlug})")
    }
}
