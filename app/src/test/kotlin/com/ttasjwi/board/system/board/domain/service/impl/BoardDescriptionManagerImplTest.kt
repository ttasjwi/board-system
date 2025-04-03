package com.ttasjwi.board.system.board.domain.service.impl

import com.ttasjwi.board.system.board.domain.exception.InvalidBoardDescriptionFormatException
import com.ttasjwi.board.system.board.domain.service.BoardDescriptionManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

@DisplayName("BoardDescriptionManagerImpl: 게시판 설명 도메인 서비스")
class BoardDescriptionManagerImplTest {

    private lateinit var boardDescriptionManager: BoardDescriptionManager

    @BeforeEach
    fun setup() {
        boardDescriptionManager = BoardDescriptionManagerImpl()
    }

    @Nested
    @DisplayName("create: 게시판 이름을 생성한다.")
    inner class Create {

        @Test
        @DisplayName("길이 유효성: ${BoardDescriptionManagerImpl.MAX_LENGTH} 자 이하")
        fun test1() {
            // given
            val value = "a".repeat(BoardDescriptionManagerImpl.MAX_LENGTH)

            // when
            val maxLengthBoardDescription = boardDescriptionManager.create(value).getOrThrow()

            // then
            assertThat(maxLengthBoardDescription).isEqualTo(value)
        }

        @Test
        @DisplayName("최대 글자수보다 글자수가 많으면 예외가 발생한다.")
        fun test2() {
            val value = "a".repeat(BoardDescriptionManagerImpl.MAX_LENGTH + 1)
            assertThrows<InvalidBoardDescriptionFormatException> { boardDescriptionManager.create(value).getOrThrow() }
        }
    }
}
