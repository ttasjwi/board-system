package com.ttasjwi.board.system.board.domain.policy.fixture

import com.ttasjwi.board.system.common.exception.CustomException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

@DisplayName("BoardSlugPolicyFixture 테스트")
class BoardSlugPolicyFixtureTest {

    private lateinit var boardSlugPolicyFixture: BoardSlugPolicyFixture

    @BeforeEach
    fun setup() {
        boardSlugPolicyFixture = BoardSlugPolicyFixture()
    }

    @Nested
    @DisplayName("게시판 슬러그 포맷을 검증한다.")
    inner class Validate {

        @Test
        @DisplayName("성공 테스트")
        fun success() {
            // given
            val value = "testslug"

            // when
            val slug = boardSlugPolicyFixture.validate(value).getOrThrow()

            // then
            assertThat(slug).isEqualTo(value)
        }


        @Test
        @DisplayName("실패 테스트")
        fun failure() {
            // given
            val value = BoardSlugPolicyFixture.ERROR_SLUG

            // when
            val exception = assertThrows<CustomException> { boardSlugPolicyFixture.validate(value).getOrThrow() }

            // then
            assertThat(exception.message).isEqualTo("게시판 슬러그 포맷 예외 - 테스트")
        }
    }
}
