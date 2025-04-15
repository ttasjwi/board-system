package com.ttasjwi.board.system.article.domain.policy

import com.ttasjwi.board.system.article.domain.exception.InvalidArticleTitleFormatException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@DisplayName("ArticleContentPolicyImpl: 게시글 제목 정책 구현체 테스트")
class ArticleTitlePolicyImplTest {

    private lateinit var articleTitlePolicy: ArticleTitlePolicy

    @BeforeEach
    fun setup() {
        articleTitlePolicy = ArticleTitlePolicyImpl()
    }

    @Nested
    @DisplayName("validate() : 게시글 제목 포맷의 유효성을 검증한다.")
    inner class Validate {

        @Test
        @DisplayName("공백으로만 구성되지 않고, ${ArticleTitlePolicyImpl.MAX_LENGTH}자를 넘지 않으면 유효하다.")
        fun testSuccess() {
            // given
            val value = "a".repeat(ArticleTitlePolicyImpl.MAX_LENGTH)

            // when
            val validatedValue = articleTitlePolicy.validate(value).getOrThrow()

            // then
            assertThat(validatedValue).isEqualTo(value)
        }


        @ParameterizedTest
        @ValueSource(strings = ["", " ", "      "])
        @DisplayName("공백으로만 구성되어선 안 된다.")
        fun blankTest(value: String) {
            assertThrows<InvalidArticleTitleFormatException> {
                articleTitlePolicy.validate(value).getOrThrow()
            }
        }


        @Test
        @DisplayName("${ArticleTitlePolicyImpl.MAX_LENGTH}자를 넘으면 예외가 발생한다.")
        fun overMaxLengthTest() {
            // given
            val value = "a".repeat(ArticleContentPolicyImpl.MAX_LENGTH + 1)

            // when
            // then
            assertThrows<InvalidArticleTitleFormatException> {
                articleTitlePolicy.validate(value).getOrThrow()
            }
        }
    }
}

