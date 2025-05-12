package com.ttasjwi.board.system.articlecomment.domain.policy

import com.ttasjwi.board.system.articlecomment.domain.exception.InvalidArticleCommentContentFormatException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

@DisplayName("[article-comment-domain] ArticleCommentContentPolicyImpl 테스트")
class ArticleCommentContentPolicyImplTest {

    private lateinit var articleContentPolicy: ArticleCommentContentPolicy

    @BeforeEach
    fun setup() {
        articleContentPolicy = ArticleCommentContentPolicyImpl()
    }

    @Nested
    @DisplayName("validate: 게시글 댓글의 포맷을 검증한다.")
    inner class Validate {

        @Test
        @DisplayName("유효성: ${ArticleCommentContentPolicyImpl.MAX_LENGTH} 자 이하")
        fun test1() {
            // given
            val value = "a".repeat(ArticleCommentContentPolicyImpl.MAX_LENGTH)

            // when
            val validatedValue = articleContentPolicy.validate(value).getOrThrow()

            // then
            assertThat(validatedValue).isEqualTo(value)
        }

        @Test
        @DisplayName("최대 글자수보다 글자수가 많으면 예외가 발생한다.")
        fun test2() {
            val value = "a".repeat(ArticleCommentContentPolicyImpl.MAX_LENGTH + 1)
            assertThrows<InvalidArticleCommentContentFormatException> {
                articleContentPolicy.validate(
                    value
                ).getOrThrow()
            }
        }
    }
}
