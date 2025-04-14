package com.ttasjwi.board.system.board.domain.policy.fixture

import com.ttasjwi.board.system.common.exception.CustomException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

@DisplayName("BoardArticleCategorySlugPolicyFixture 테스트")
class BoardArticleCategorySlugPolicyFixtureTest {


    private lateinit var boardArticleCategorySlugPolicyFixture: BoardArticleCategorySlugPolicyFixture

    @BeforeEach
    fun setup() {
        boardArticleCategorySlugPolicyFixture = BoardArticleCategorySlugPolicyFixture()
    }

    @Nested
    @DisplayName("게시글 카테고리 슬러그 포맷을 검증한다.")
    inner class Validate {

        @Test
        @DisplayName("성공 테스트")
        fun success() {
            // given
            val value = "testslug"

            // when
            val slug = boardArticleCategorySlugPolicyFixture.validate(value).getOrThrow()

            // then
            assertThat(slug).isEqualTo(value)
        }


        @Test
        @DisplayName("실패 테스트")
        fun failure() {
            // given
            val value = BoardArticleCategorySlugPolicyFixture.ERROR_SLUG

            // when
            val exception = assertThrows<CustomException> { boardArticleCategorySlugPolicyFixture.validate(value).getOrThrow() }

            // then
            assertThat(exception.message).isEqualTo("픽스쳐 예외 : 잘못된 게시글카테고리 슬러그 포맷")
        }
    }
}
