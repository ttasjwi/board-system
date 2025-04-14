package com.ttasjwi.board.system.board.domain.policy

import com.ttasjwi.board.system.board.domain.exception.InvalidBoardNameFormatException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@DisplayName("BoardNamePolicyImpl: 게시판 이름 도메인 정책")
class BoardNamePolicyImplTest {

    private lateinit var boardNamePolicy: BoardNamePolicy

    @BeforeEach
    fun setup() {
        boardNamePolicy = BoardNamePolicyImpl()
    }

    @Nested
    @DisplayName("validate: 게시판 이름을 검증한다.")
    inner class Validate {

        @Test
        @DisplayName("길이 유효성: 양끝 공백을 제거했을 때 ${BoardNamePolicyImpl.MIN_LENGTH}자 이상, ${BoardNamePolicyImpl.MAX_LENGTH}자 이하")
        fun test1() {
            // given
            val minLengthValue = "a".repeat(BoardNamePolicyImpl.MIN_LENGTH)
            val maxLengthValue = "a".repeat(BoardNamePolicyImpl.MAX_LENGTH)

            // when
            val minLengthBoardName = boardNamePolicy.validate(minLengthValue).getOrThrow()
            val maxLengthBoardName = boardNamePolicy.validate(maxLengthValue).getOrThrow()

            // then
            assertThat(minLengthBoardName).isEqualTo(minLengthValue)
            assertThat(maxLengthBoardName).isEqualTo(maxLengthValue)
        }

        @Test
        @DisplayName("양 끝 공백을 허용하지 않는다")
        fun test2() {
            val value = "         야호           "
            assertThrows<InvalidBoardNameFormatException> { boardNamePolicy.validate(value).getOrThrow() }
        }

        @Test
        @DisplayName("최소 글자수보다 글자수가 적으면 예외가 발생한다.")
        fun test3() {
            val value = "a".repeat(BoardNamePolicyImpl.MIN_LENGTH - 1)
            assertThrows<InvalidBoardNameFormatException> { boardNamePolicy.validate(value).getOrThrow() }
        }

        @Test
        @DisplayName("최대 글자수보다 글자수가 많으면 예외가 발생한다.")
        fun test4() {
            val value = "a".repeat(BoardNamePolicyImpl.MAX_LENGTH + 1)

            assertThrows<InvalidBoardNameFormatException> { boardNamePolicy.validate(value).getOrThrow() }
        }


        @ParameterizedTest
        @ValueSource(strings = ["a badfc", "1 as가f4", "Abc4/"])
        @DisplayName("양 끝 공백을 제거했을 때, 영어, 숫자, 완성형 한글, 스페이스, / 를 허용한다.")
        fun test5(value: String) {
            // given
            // when
            val boardName = boardNamePolicy.validate(value).getOrThrow()

            // then
            assertThat(boardName).isEqualTo(value)
        }

        @ParameterizedTest
        @ValueSource(strings = ["ㅇㅅㅇ", "ㅔㅔㅔ", "ㅠㅠㅠ", "aㅔ잉"])
        @DisplayName("완성형 한글이 아닌 문자는 포함될 수 없다")
        fun test6(value: String) {
            assertThrows<InvalidBoardNameFormatException> { boardNamePolicy.validate(value).getOrThrow() }
        }

        @ParameterizedTest
        @ValueSource(strings = ["!aas", "122!", "23da@", "<12334", "_1236a"])
        @DisplayName("특수문자는 사용할 수 없다. 포함되면 생성 시 예외 발생")
        fun test7(value: String) {
            assertThrows<InvalidBoardNameFormatException> { boardNamePolicy.validate(value).getOrThrow() }
        }
    }
}
