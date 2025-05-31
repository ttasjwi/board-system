package com.ttasjwi.board.system.board.domain.exception

import com.ttasjwi.board.system.board.domain.policy.ArticleCategorySlugPolicyImpl
import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[board-domain] InvalidArticleCategorySlugFormatException: 게시글 카테고리 슬러그 포맷이 유효하지 않을 때의 예외")
class InvalidArticleCategorySlugFormatExceptionTest {

    @Test
    @DisplayName("예외 기본값 테스트")
    fun test() {
        val exception = InvalidArticleCategorySlugFormatException()

        assertThat(exception.status).isEqualTo(ErrorStatus.BAD_REQUEST)
        assertThat(exception.code).isEqualTo("Error.InvalidArticleCategorySlugFormat")
        assertThat(exception.source).isEqualTo("articleCategorySlug")
        assertThat(exception.args).containsExactly(
            ArticleCategorySlugPolicyImpl.MIN_LENGTH,
            ArticleCategorySlugPolicyImpl.MAX_LENGTH
        )
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo(
            "게시글 카테고리 슬러그는 ${ArticleCategorySlugPolicyImpl.MIN_LENGTH} 자 이상 " +
                    "${ArticleCategorySlugPolicyImpl.MAX_LENGTH} 자 이하의 영어 또는 숫자만 허용됩니다."
        )
    }
}

