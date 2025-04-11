package com.ttasjwi.board.system.board.domain.service.fixture

import com.ttasjwi.board.system.common.exception.CustomException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

@DisplayName("BoardSlugManager 픽스쳐 테스트")
class BoardSlugManagerFixtureTest {

    private lateinit var boardSlugManagerFixture: BoardSlugManagerFixture

    @BeforeEach
    fun setup() {
        boardSlugManagerFixture = BoardSlugManagerFixture()
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
            val slug = boardSlugManagerFixture.validate(value).getOrThrow()

            // then
            assertThat(slug).isEqualTo(value)
        }


        @Test
        @DisplayName("실패 테스트")
        fun failure() {
            // given
            val value = BoardSlugManagerFixture.ERROR_SLUG

            // when
            val exception = assertThrows<CustomException> { boardSlugManagerFixture.validate(value).getOrThrow() }

            // then
            assertThat(exception.message).isEqualTo("게시판 슬러그 포맷 예외 - 테스트")
        }
    }
}
