package com.ttasjwi.board.system.board.domain.policy

import com.ttasjwi.board.system.board.domain.exception.InvalidArticleCategoryNameFormatException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

@DisplayName("ArticleCategoryNameImpl : 게시글 카테고리명 정책")
class ArticleCategoryNamePolicyImplTest {

    private lateinit var articleCategoryNamePolicy: ArticleCategoryNamePolicy

    @BeforeEach
    fun setup() {
        articleCategoryNamePolicy = ArticleCategoryNamePolicyImpl()
    }

    @Nested
    @DisplayName("validate : 유효성을 검증하고, 성공하면 원본값을 반환한 Result / 실패하면 예외를 담은 Result 반환")
    inner class Validate {

        @Test
        @DisplayName("성공테스트")
        fun test1() {
            // given
            val name = "a".repeat(ArticleCategoryNamePolicyImpl.MAX_LENGTH - 1)

            // when
            val result = articleCategoryNamePolicy.validate(name)

            // then
            assertThat(result.isSuccess).isTrue()
            assertThat(result.getOrThrow()).isEqualTo(name)
        }

        @Test
        @DisplayName("양끝 공백 허용 안 함.")
        fun test2() {
            // given
            val name = " 이름 "

            // when
            val result = articleCategoryNamePolicy.validate(name)
            // then
            assertThat(result.isFailure).isTrue()
            assertThrows<InvalidArticleCategoryNameFormatException> { result.getOrThrow() }
        }


        @Test
        @DisplayName("길이가 ${ArticleCategoryNamePolicyImpl.MAX_LENGTH} 자를 넘으면 안 된다.")
        fun test3() {
            // given
            val name = "a".repeat(ArticleCategoryNamePolicyImpl.MAX_LENGTH + 1)

            // when
            val result = articleCategoryNamePolicy.validate(name)

            // then
            assertThat(result.isFailure).isTrue()
            assertThrows<InvalidArticleCategoryNameFormatException> { result.getOrThrow() }
        }

    }

}
