package com.ttasjwi.board.system.board.domain.policy.fixture

import com.ttasjwi.board.system.common.exception.CustomException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

class BoardArticleCategoryNamePolicyFixtureTest {

    private lateinit var boardArticleCategoryNamePolicyFixture: BoardArticleCategoryNamePolicyFixture

    @BeforeEach
    fun setup() {
        boardArticleCategoryNamePolicyFixture = BoardArticleCategoryNamePolicyFixture()
    }

    @Nested
    @DisplayName("게시글 카테고리 슬러그 포맷을 검증한다.")
    inner class Validate {

        @Test
        @DisplayName("성공 테스트")
        fun success() {
            // given
            val name = "테스트이름"

            // when
            val validatedName = boardArticleCategoryNamePolicyFixture.validate(name).getOrThrow()

            // then
            assertThat(validatedName).isEqualTo(name)
        }


        @Test
        @DisplayName("실패 테스트")
        fun failure() {
            // given
            val name = BoardArticleCategoryNamePolicyFixture.ERROR_NAME

            // when
            val exception = assertThrows<CustomException> { boardArticleCategoryNamePolicyFixture.validate(name).getOrThrow() }

            // then
            assertThat(exception.message).isEqualTo("픽스쳐 예외 : 잘못된 게시글 카테고리 이름 포맷")
        }
    }
}
