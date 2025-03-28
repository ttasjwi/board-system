package com.ttasjwi.board.system.board.domain.model

import com.ttasjwi.board.system.board.domain.model.fixture.boardNameFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("게시판 이름 테스트")
class BoardNameTest {

    @Test
    @DisplayName("restore: 값으로부터 BoardName 인스턴스를 복원한다.")
    fun testRestore() {
        val value = "게임"
        val name = BoardName.restore(value)
        assertThat(name.value).isEqualTo(value)
    }

    @Nested
    @DisplayName("equals & hashCode")
    inner class EqualsAndHashCode {

        @Test
        @DisplayName("참조가 같으면 동등하다.")
        fun test1() {
            val name = boardNameFixture("게임")
            assertThat(name).isEqualTo(name)
            assertThat(name.hashCode()).isEqualTo(name.hashCode())
        }

        @Test
        @DisplayName("타입이 같고 값이 같으면 동등하다")
        fun test2() {
            val name1 = boardNameFixture("게임")
            val name2 = boardNameFixture("게임")
            val name3 = boardNameFixture("게임")

            assertThat(name1).isEqualTo(name2)
            assertThat(name1).isEqualTo(name3)
            assertThat(name1.hashCode()).isEqualTo(name2.hashCode())
            assertThat(name1.hashCode()).isEqualTo(name3.hashCode())
        }

        @Test
        @DisplayName("타입이 BoardName 이면서 값이 다르면 동등하지 않다")
        fun test3() {
            val name1 = boardNameFixture("게임")
            val name2 = boardNameFixture("서양음악")
            val name3 = boardNameFixture("역사")

            assertThat(name1).isNotEqualTo(name2)
            assertThat(name1).isNotEqualTo(name3)
        }

        @Test
        @DisplayName("타입이 null 이면 동등하지 않다")
        fun test4() {
            val name = boardNameFixture("게임")
            val other = null

            assertThat(name).isNotEqualTo(other)
        }

        @Test
        @DisplayName("타입이 BoardName 이 아니면 동등하지 않다")
        fun test5() {
            val name = boardNameFixture("게임")
            val other = 1L

            assertThat(name).isNotEqualTo(other)
        }
    }

    @Test
    @DisplayName("toString: 디버깅을 위한 문자열 반환")
    fun testToString() {
        val value = "게임"
        val name = boardNameFixture(value)
        assertThat(name.toString()).isEqualTo("BoardName(value=$value)")
    }
}
