package com.ttasjwi.board.system.board.domain.service.fixture

import com.ttasjwi.board.system.board.domain.model.fixture.boardNameFixture
import com.ttasjwi.board.system.board.domain.service.BoardNameCreator
import com.ttasjwi.board.system.common.exception.CustomException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

@DisplayName("BoardNameCreator 픽스쳐 테스트")
class BoardNameCreatorFixtureTest {

    private lateinit var boardNameCreatorFixture: BoardNameCreator

    @BeforeEach
    fun setup() {
        boardNameCreatorFixture = BoardNameCreatorFixture()
    }

    @Nested
    @DisplayName("게시판 이름을 생성한다.")
    inner class Create {

        @Test
        @DisplayName("성공 테스트")
        fun success() {
            // given
            val value = "경제"

            // when
            val name = boardNameCreatorFixture.create(value).getOrThrow()

            // then
            assertThat(name).isEqualTo(boardNameFixture(value))
        }


        @Test
        @DisplayName("실패 테스트")
        fun failure() {
            // given
            val value = BoardNameCreatorFixture.ERROR_NAME

            // when
            val exception = assertThrows<CustomException> { boardNameCreatorFixture.create(value).getOrThrow() }

            // then
            assertThat(exception.message).isEqualTo("게시판 이름 포맷 예외 - 테스트")
        }
    }
}
