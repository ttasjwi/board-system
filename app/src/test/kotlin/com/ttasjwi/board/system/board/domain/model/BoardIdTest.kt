package com.ttasjwi.board.system.board.domain.model

import com.ttasjwi.board.system.board.domain.model.fixture.boardIdFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("게시판 식별자 테스트")
class BoardIdTest {

    @Test
    @DisplayName("restore: 값으로부터 BoardId 인스턴스를 복원한다.")
    fun testRestore() {
        val value = 134L
        val id = BoardId.restore(value)
        assertThat(id.value).isEqualTo(value)
    }

    @Nested
    @DisplayName("equals & hashCode")
    inner class EqualsAndHashCode {

        @Test
        @DisplayName("참조가 같으면 동등하다.")
        fun test1() {
            val id = boardIdFixture(133L)
            assertThat(id).isEqualTo(id)
            assertThat(id.hashCode()).isEqualTo(id.hashCode())
        }

        @Test
        @DisplayName("타입이 같고 값이 같으면 동등하다")
        fun test2() {
            val id1 = boardIdFixture(123L)
            val id2 = boardIdFixture(123L)
            val id3 = boardIdFixture(123L)

            assertThat(id1).isEqualTo(id2)
            assertThat(id1).isEqualTo(id3)
            assertThat(id1.hashCode()).isEqualTo(id2.hashCode())
            assertThat(id1.hashCode()).isEqualTo(id3.hashCode())
        }

        @Test
        @DisplayName("타입이 BoardId 이면서 값이 다르면 동등하지 않다")
        fun test3() {
            val id1 = boardIdFixture(123L)
            val id2 = boardIdFixture(456L)
            val id3 = boardIdFixture(789L)

            assertThat(id1).isNotEqualTo(id2)
            assertThat(id1).isNotEqualTo(id3)
        }

        @Test
        @DisplayName("타입이 null 이면 동등하지 않다")
        fun test4() {
            val id = boardIdFixture(123L)
            val other = null

            assertThat(id).isNotEqualTo(other)
        }

        @Test
        @DisplayName("타입이 BoardId 이 아니면 동등하지 않다")
        fun test5() {
            val id = boardIdFixture(123L)
            val other = "문자열"

            assertThat(id).isNotEqualTo(other)
        }
    }

    @Test
    @DisplayName("toString: 디버깅을 위한 문자열 반환")
    fun testToString() {
        val value = 123L
        val id = boardIdFixture(value)
        assertThat(id.toString()).isEqualTo("BoardId(value=$value)")
    }
}
