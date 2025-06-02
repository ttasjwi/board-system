package com.ttasjwi.board.system.article.domain.policy

import com.ttasjwi.board.system.article.domain.exception.InvalidArticleContentFormatException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

@DisplayName("[article-domain] ArticleContentPolicyImpl: 게시글 본문 정책 구현체 테스트")
class ArticleContentPolicyImplTest {

    private lateinit var articleContentPolicy: ArticleContentPolicy

    @BeforeEach
    fun setup() {
        articleContentPolicy = ArticleContentPolicyImpl()
    }

    @Nested
    @DisplayName("validate: 게시글 본문의 포맷을 검증한다.")
    inner class Validate {

        @Test
        @DisplayName("유효성: ${ArticleContentPolicyImpl.MAX_LENGTH} 자 이하")
        fun test1() {
            // given
            val value = "a".repeat(ArticleContentPolicyImpl.MAX_LENGTH)

            // when
            val validatedValue = articleContentPolicy.validate(value).getOrThrow()

            // then
            assertThat(validatedValue).isEqualTo(value)
        }

        @Test
        @DisplayName("최대 글자수보다 글자수가 많으면 예외가 발생한다.")
        fun test2() {
            val value = "a".repeat(ArticleContentPolicyImpl.MAX_LENGTH + 1)
            assertThrows<InvalidArticleContentFormatException> {
                articleContentPolicy.validate(
                    value
                ).getOrThrow()
            }
        }
    }
}
