package com.ttasjwi.board.system.board.domain.policy

import com.ttasjwi.board.system.board.domain.exception.InvalidBoardDescriptionFormatException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

@DisplayName("[board-domain] BoardDescriptionPolicyImpl: 게시판 설명 도메인 정책")
class BoardDescriptionPolicyImplTest {

    private lateinit var boardDescriptionPolicy: BoardDescriptionPolicy

    @BeforeEach
    fun setup() {
        boardDescriptionPolicy = BoardDescriptionPolicyImpl()
    }

    @Nested
    @DisplayName("create: 게시판 이름을 생성한다.")
    inner class Create {

        @Test
        @DisplayName("길이 유효성: ${BoardDescriptionPolicyImpl.MAX_LENGTH} 자 이하")
        fun test1() {
            // given
            val value = "a".repeat(BoardDescriptionPolicyImpl.MAX_LENGTH)

            // when
            val maxLengthBoardDescription = boardDescriptionPolicy.create(value).getOrThrow()

            // then
            assertThat(maxLengthBoardDescription).isEqualTo(value)
        }

        @Test
        @DisplayName("최대 글자수보다 글자수가 많으면 예외가 발생한다.")
        fun test2() {
            val value = "a".repeat(BoardDescriptionPolicyImpl.MAX_LENGTH + 1)
            assertThrows<InvalidBoardDescriptionFormatException> { boardDescriptionPolicy.create(value).getOrThrow() }
        }
    }
}
