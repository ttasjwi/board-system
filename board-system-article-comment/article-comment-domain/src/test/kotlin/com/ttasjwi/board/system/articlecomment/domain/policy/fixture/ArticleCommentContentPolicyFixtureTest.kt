package com.ttasjwi.board.system.articlecomment.domain.policy.fixture

import com.ttasjwi.board.system.common.exception.CustomException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

@DisplayName("[article-comment-domain] ArticleCommentContentPolicyFixture 테스트")
class ArticleCommentContentPolicyFixtureTest {

    private lateinit var articleCommentContentPolicyFixture: ArticleCommentContentPolicyFixture

    @BeforeEach
    fun setup() {
        articleCommentContentPolicyFixture = ArticleCommentContentPolicyFixture()
    }

    @Nested
    @DisplayName("validate: 게시글 댓글의 포맷을 검증한다.")
    inner class Validate {

        @Test
        @DisplayName("성공 테스트")
        fun test1() {
            // given
            val value = "본문"

            // when
            val validatedValue = articleCommentContentPolicyFixture.validate(value).getOrThrow()

            // then
            assertThat(validatedValue).isEqualTo(value)
        }

        @Test
        @DisplayName("실패하는 경우 : ERROR_CONTENT 가 전달됐을 때")
        fun test2() {
            // given
            val value = ArticleCommentContentPolicyFixture.ERROR_CONTENT

            // when
            val ex = assertThrows<CustomException> {
                articleCommentContentPolicyFixture.validate(
                    value
                ).getOrThrow()
            }
            // then
            assertThat(ex.debugMessage).isEqualTo("픽스쳐 예외 : 게시글 댓글의 포맷이 유효하지 않습니다.")
        }
    }
}
