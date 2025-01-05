package com.ttasjwi.board.system.board.domain.service.impl

import com.ttasjwi.board.system.board.domain.exception.InvalidBoardDescriptionFormatException
import com.ttasjwi.board.system.board.domain.model.BoardDescription
import com.ttasjwi.board.system.board.domain.model.fixture.boardDescriptionFixture
import com.ttasjwi.board.system.board.domain.service.BoardDescriptionCreator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

@DisplayName("BoardDescriptionCreatorImpl: 게시판 설명 생성기")
class BoardDescriptionCreatorImplTest {

    private lateinit var boardDescriptionCreator: BoardDescriptionCreator

    @BeforeEach
    fun setup() {
        boardDescriptionCreator = BoardDescriptionCreatorImpl()
    }

    @Nested
    @DisplayName("create: 게시판 이름을 생성한다.")
    inner class Create {

        @Test
        @DisplayName("길이 유효성: ${BoardDescription.MAX_LENGTH} 자 이하")
        fun test1() {
            // given
            val value = "a".repeat(BoardDescription.MAX_LENGTH)

            // when
            val maxLengthBoardDescription = boardDescriptionCreator.create(value).getOrThrow()

            // then
            assertThat(maxLengthBoardDescription).isEqualTo(boardDescriptionFixture(value))
        }

        @Test
        @DisplayName("최대 글자수보다 글자수가 많으면 예외가 발생한다.")
        fun test2() {
            val value = "a".repeat(BoardDescription.MAX_LENGTH + 1)
            assertThrows<InvalidBoardDescriptionFormatException> { boardDescriptionCreator.create(value).getOrThrow() }
        }
    }
}
