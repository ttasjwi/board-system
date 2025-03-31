package com.ttasjwi.board.system.board.domain.model

import com.ttasjwi.board.system.board.domain.model.fixture.boardDescriptionFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("게시판 설명 테스트")
class BoardDescriptionTest {

    @Test
    @DisplayName("restore: 값으로부터 BoardDescription 인스턴스를 복원한다.")
    fun testRestore() {
        val value = "게시판 설명"
        val description = BoardDescription.restore(value)
        assertThat(description.value).isEqualTo(value)
    }

    @Nested
    @DisplayName("equals & hashCode")
    inner class EqualsAndHashCode {

        @Test
        @DisplayName("참조가 같으면 동등하다.")
        fun test1() {
            val description = boardDescriptionFixture("게시판 설명이에용")
            assertThat(description).isEqualTo(description)
            assertThat(description.hashCode()).isEqualTo(description.hashCode())
        }

        @Test
        @DisplayName("타입이 같고 값이 같으면 동등하다")
        fun test2() {
            val description1 = boardDescriptionFixture("설명")
            val description2 = boardDescriptionFixture("설명")
            val description3 = boardDescriptionFixture("설명")

            assertThat(description1).isEqualTo(description2)
            assertThat(description1).isEqualTo(description3)
            assertThat(description1.hashCode()).isEqualTo(description2.hashCode())
            assertThat(description1.hashCode()).isEqualTo(description3.hashCode())
        }

        @Test
        @DisplayName("타입이 BoardDescription 이면서 값이 다르면 동등하지 않다")
        fun test3() {
            val description1 = boardDescriptionFixture("설명1")
            val description2 = boardDescriptionFixture("설명2")
            val description3 = boardDescriptionFixture("설명3")

            assertThat(description1).isNotEqualTo(description2)
            assertThat(description1).isNotEqualTo(description3)
        }

        @Test
        @DisplayName("타입이 null 이면 동등하지 않다")
        fun test4() {
            val description = boardDescriptionFixture("게시판 설명")
            val other = null

            assertThat(description).isNotEqualTo(other)
        }

        @Test
        @DisplayName("타입이 BoardDescription 이 아니면 동등하지 않다")
        fun test5() {
            val description = boardDescriptionFixture("게시판 설명")
            val other = 1L

            assertThat(description).isNotEqualTo(other)
        }
    }

    @Test
    @DisplayName("toString: 디버깅을 위한 문자열 반환")
    fun testToString() {
        val value = "게시판 설명이에요오오옹"
        val description = boardDescriptionFixture(value)
        assertThat(description.toString()).isEqualTo("BoardDescription(value=$value)")
    }
}
