package com.ttasjwi.board.system.board.domain.service.impl

import com.ttasjwi.board.system.board.domain.exception.InvalidBoardSlugFormatException
import com.ttasjwi.board.system.board.domain.model.BoardSlug
import com.ttasjwi.board.system.board.domain.model.fixture.boardSlugFixture
import com.ttasjwi.board.system.board.domain.service.BoardSlugCreator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@DisplayName("BoardSlugCreatorImpl: 게시판 슬러그 생성기")
class BoardSlugCreatorImplTest {

    private lateinit var boardSlugCreator: BoardSlugCreator

    @BeforeEach
    fun setup() {
        boardSlugCreator = BoardSlugCreatorImpl()
    }

    @Nested
    @DisplayName("create: 게시판 슬러그를 생성한다.")
    inner class Create {


        @Test
        @DisplayName("길이 유효성: ${BoardSlug.MIN_LENGTH}자 이상, ${BoardSlug.MAX_LENGTH}자 이하")
        fun test1() {
            // given
            val minLengthValue = "a".repeat(BoardSlug.MIN_LENGTH)
            val maxLengthValue = "a".repeat(BoardSlug.MAX_LENGTH)

            // when
            val minLengthBoardSlug = boardSlugCreator.create(minLengthValue).getOrThrow()
            val maxLengthBoardSlug = boardSlugCreator.create(maxLengthValue).getOrThrow()

            // then
            assertThat(minLengthBoardSlug).isEqualTo(boardSlugFixture(minLengthValue))
            assertThat(maxLengthBoardSlug).isEqualTo(boardSlugFixture(maxLengthValue))
        }

        @ParameterizedTest
        @ValueSource(strings = ["abcd", "123456zzz", "a12345", "9564"])
        @DisplayName("영어소문자, 숫자 만을 허용한다.")
        fun test2(value: String) {
            // when
            // then
            val boardSlug = boardSlugCreator.create(value).getOrThrow()
            assertThat(boardSlug).isEqualTo(boardSlugFixture(value))
        }

        @Test
        @DisplayName("최소 글자수보다 글자수가 적으면 예외가 발생한다.")
        fun test3() {
            val value = "a".repeat(BoardSlug.MIN_LENGTH - 1)
            assertThrows<InvalidBoardSlugFormatException> { boardSlugCreator.create(value).getOrThrow() }
        }

        @Test
        @DisplayName("최대 글자수보다 글자수가 많으면 예외가 발생한다.")
        fun test4() {
            val value = "a".repeat(BoardSlug.MAX_LENGTH + 1)

            assertThrows<InvalidBoardSlugFormatException> { boardSlugCreator.create(value).getOrThrow() }
        }

        @ParameterizedTest
        @ValueSource(strings = ["Aaa", "Bbb", "CC9C"])
        @DisplayName("영어 대문자는 포함될 수 없다")
        fun test5(value: String) {
            assertThrows<InvalidBoardSlugFormatException> { boardSlugCreator.create(value).getOrThrow() }
        }

        @ParameterizedTest
        @ValueSource(strings = ["aㅇㅅㅇ", "무야호", "ab0꺅"])
        @DisplayName("한글은 포함될 수 없다")
        fun test6(value: String) {
            assertThrows<InvalidBoardSlugFormatException> { boardSlugCreator.create(value).getOrThrow() }
        }

        @ParameterizedTest
        @ValueSource(strings = ["!aas", "122!", "23da@", "<12334", "_1236a"])
        @DisplayName("특수문자는 사용할 수 없다. 포함되면 생성 시 예외 발생")
        fun test7(value: String) {
            assertThrows<InvalidBoardSlugFormatException> { boardSlugCreator.create(value).getOrThrow() }
        }
    }
}
