package com.ttasjwi.board.system.board.domain.service.fixture

import com.ttasjwi.board.system.board.domain.service.BoardNameManager
import com.ttasjwi.board.system.global.exception.CustomException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

@DisplayName("BoardNameManager 픽스쳐 테스트")
class BoardNameManagerFixtureTest {

    private lateinit var boardNameManagerFixture: BoardNameManager

    @BeforeEach
    fun setup() {
        boardNameManagerFixture = BoardNameManagerFixture()
    }

    @Nested
    @DisplayName("게시판 이름을 검증한다.")
    inner class Validate {

        @Test
        @DisplayName("성공 테스트")
        fun success() {
            // given
            val value = "경제"

            // when
            val name = boardNameManagerFixture.validate(value).getOrThrow()

            // then
            assertThat(name).isEqualTo(value)
        }


        @Test
        @DisplayName("실패 테스트")
        fun failure() {
            // given
            val value = BoardNameManagerFixture.ERROR_NAME

            // when
            val exception = assertThrows<CustomException> { boardNameManagerFixture.validate(value).getOrThrow() }

            // then
            assertThat(exception.message).isEqualTo("게시판 이름 포맷 예외 - 테스트")
        }
    }
}
