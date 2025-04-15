package com.ttasjwi.board.system.article.domain.policy.fixture

import com.ttasjwi.board.system.common.exception.CustomException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

@DisplayName("ArticleTitlePolicyFixture: 게시글 제목 정책 픽스쳐")
class ArticleTitlePolicyFixtureTest {

    private lateinit var articleTitlePolicyFixture: ArticleTitlePolicyFixture

    @BeforeEach
    fun setup() {
        articleTitlePolicyFixture = ArticleTitlePolicyFixture()
    }

    @Nested
    @DisplayName("validate() : 게시글 제목 포맷의 유효성을 검증한다.")
    inner class Validate {

        @Test
        @DisplayName("성공 테스트")
        fun testSuccess() {
            // given
            val value = "제목"

            // when
            val validatedValue = articleTitlePolicyFixture.validate(value).getOrThrow()

            // then
            assertThat(validatedValue).isEqualTo(value)
        }

        @Test
        @DisplayName("실패하는 경우 : ERROR_TITLE 이 전달됐을 때")
        fun testFailure() {
            // given
            val value = ArticleTitlePolicyFixture.ERROR_TITLE

            // when
            val ex = assertThrows<CustomException> {
                articleTitlePolicyFixture.validate(value).getOrThrow()
            }

            // then
            assertThat(ex.debugMessage).isEqualTo("픽스쳐 예외 : 게시글 제목의 포맷이 유효하지 않습니다.")
        }
    }
}
