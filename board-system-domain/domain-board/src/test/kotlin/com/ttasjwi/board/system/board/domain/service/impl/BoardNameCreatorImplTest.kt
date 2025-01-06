package com.ttasjwi.board.system.board.domain.service.impl

import com.ttasjwi.board.system.board.domain.exception.InvalidBoardNameFormatException
import com.ttasjwi.board.system.board.domain.model.BoardName
import com.ttasjwi.board.system.board.domain.model.fixture.boardNameFixture
import com.ttasjwi.board.system.board.domain.service.BoardNameCreator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@DisplayName("BoardNameCreatorImpl: 게시판 이름 생성기")
class BoardNameCreatorImplTest {

    private lateinit var boardNameCreator: BoardNameCreator

    @BeforeEach
    fun setup() {
        boardNameCreator = BoardNameCreatorImpl()
    }

    @Nested
    @DisplayName("create: 게시판 이름을 생성한다.")
    inner class Create {


        @Test
        @DisplayName("길이 유효성: 양끝 공백을 제거했을 때 ${BoardName.MIN_LENGTH}자 이상, ${BoardName.MAX_LENGTH}자 이하")
        fun test1() {
            // given
            val minLengthValue = "a".repeat(BoardName.MIN_LENGTH)
            val maxLengthValue = "a".repeat(BoardName.MAX_LENGTH)

            // when
            val minLengthBoardName = boardNameCreator.create(minLengthValue).getOrThrow()
            val maxLengthBoardName = boardNameCreator.create(maxLengthValue).getOrThrow()

            // then
            assertThat(minLengthBoardName).isEqualTo(boardNameFixture(minLengthValue))
            assertThat(maxLengthBoardName).isEqualTo(boardNameFixture(maxLengthValue))
        }

        @Test
        @DisplayName("공백만으로 구성되면 안 된다.")
        fun test2() {
            val value = "                             "
            assertThrows<InvalidBoardNameFormatException> { boardNameCreator.create(value).getOrThrow() }
        }

        @Test
        @DisplayName("최소 글자수보다 글자수가 적으면 예외가 발생한다.")
        fun test3() {
            val value = "a".repeat(BoardName.MIN_LENGTH - 1)

            assertThrows<InvalidBoardNameFormatException> { boardNameCreator.create(value).getOrThrow() }
        }

        @Test
        @DisplayName("최대 글자수보다 글자수가 많으면 예외가 발생한다.")
        fun test4() {
            val value = "a".repeat(BoardName.MAX_LENGTH + 1)

            assertThrows<InvalidBoardNameFormatException> { boardNameCreator.create(value).getOrThrow() }
        }


        @ParameterizedTest
        @ValueSource(strings = ["a badfc", "1 as가f4", "Abc4/"])
        @DisplayName("양 끝 공백을 제거했을 때, 영어, 숫자, 완성형 한글, 스페이스, / 를 허용한다.")
        fun test5(value: String) {
            // given
            // when
            val boardName = boardNameCreator.create(value).getOrThrow()

            // then
            assertThat(boardName).isEqualTo(boardNameFixture(value))
        }

        @ParameterizedTest
        @ValueSource(strings = ["ㅇㅅㅇ", "ㅔㅔㅔ", "ㅠㅠㅠ", "aㅔ잉"])
        @DisplayName("완성형 한글이 아닌 문자는 포함될 수 없다")
        fun test6(value: String) {
            assertThrows<InvalidBoardNameFormatException> { boardNameCreator.create(value).getOrThrow() }
        }

        @ParameterizedTest
        @ValueSource(strings = ["!aas", "122!", "23da@", "<12334", "_1236a"])
        @DisplayName("특수문자는 사용할 수 없다. 포함되면 생성 시 예외 발생")
        fun test7(value: String) {
            assertThrows<InvalidBoardNameFormatException> { boardNameCreator.create(value).getOrThrow() }
        }
    }
}
