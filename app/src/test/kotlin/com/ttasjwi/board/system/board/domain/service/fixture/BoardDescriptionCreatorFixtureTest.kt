package com.ttasjwi.board.system.board.domain.service.fixture

import com.ttasjwi.board.system.board.domain.model.fixture.boardDescriptionFixture
import com.ttasjwi.board.system.board.domain.service.BoardDescriptionCreator
import com.ttasjwi.board.system.common.exception.CustomException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

@DisplayName("BoardDescriptionCreator 픽스쳐 테스트")
class BoardDescriptionCreatorFixtureTest {

    private lateinit var boardDescriptionCreatorFixture: BoardDescriptionCreator

    @BeforeEach
    fun setup() {
        boardDescriptionCreatorFixture = BoardDescriptionCreatorFixture()
    }

    @Nested
    @DisplayName("게시판 설명을 생성한다.")
    inner class Create {


        @Test
        @DisplayName("성공 테스트")
        fun success() {
            // given
            val value = "설명"

            // when
            val description = boardDescriptionCreatorFixture.create(value).getOrThrow()

            // then
            assertThat(description).isEqualTo(boardDescriptionFixture(value))
        }


        @Test
        @DisplayName("실패 테스트")
        fun failure() {
            // given
            val value = BoardDescriptionCreatorFixture.ERROR_DESCRIPTION

            // when
            val exception = assertThrows<CustomException> { boardDescriptionCreatorFixture.create(value).getOrThrow() }

            // then
            assertThat(exception.message).isEqualTo("게시판 설명 포맷 예외 - 테스트")
        }
    }
}
