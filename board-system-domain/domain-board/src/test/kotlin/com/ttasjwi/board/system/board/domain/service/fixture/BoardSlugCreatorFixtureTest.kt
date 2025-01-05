package com.ttasjwi.board.system.board.domain.service.fixture

import com.ttasjwi.board.system.board.domain.model.fixture.boardSlugFixture
import com.ttasjwi.board.system.core.exception.CustomException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*


@DisplayName("BoardSlugCreator 픽스쳐 테스트")
class BoardSlugCreatorFixtureTest {

    private lateinit var boardSlugCreatorFixture: BoardSlugCreatorFixture

    @BeforeEach
    fun setup() {
        boardSlugCreatorFixture = BoardSlugCreatorFixture()
    }

    @Nested
    @DisplayName("게시판 슬러그를 생성한다.")
    inner class Create {

        @Test
        @DisplayName("성공 테스트")
        fun success() {
            // given
            val value = "testslug"

            // when
            val slug = boardSlugCreatorFixture.create(value).getOrThrow()

            // then
            assertThat(slug).isEqualTo(boardSlugFixture(value))
        }


        @Test
        @DisplayName("실패 테스트")
        fun failure() {
            // given
            val value = BoardSlugCreatorFixture.ERROR_SLUG

            // when
            val exception = assertThrows<CustomException> { boardSlugCreatorFixture.create(value).getOrThrow() }

            // then
            assertThat(exception.message).isEqualTo("게시판 슬러그 포맷 예외 - 테스트")
        }
    }
}
