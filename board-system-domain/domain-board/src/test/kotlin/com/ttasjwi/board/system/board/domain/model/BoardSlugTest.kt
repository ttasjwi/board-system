package com.ttasjwi.board.system.board.domain.model

import com.ttasjwi.board.system.board.domain.model.fixture.boardSlugFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("게시판 슬러그 테스트")
class BoardSlugTest {

    @Test
    @DisplayName("restore: 값으로부터 BoardSlug 인스턴스를 복원한다.")
    fun testRestore() {
        val value = "game"
        val slug = BoardSlug.restore(value)
        assertThat(slug.value).isEqualTo(value)
    }

    @Nested
    @DisplayName("equals & hashCode")
    inner class EqualsAndHashCode {

        @Test
        @DisplayName("참조가 같으면 동등하다.")
        fun test1() {
            val slug = boardSlugFixture("game")
            assertThat(slug).isEqualTo(slug)
            assertThat(slug.hashCode()).isEqualTo(slug.hashCode())
        }

        @Test
        @DisplayName("타입이 같고 값이 같으면 동등하다")
        fun test2() {
            val slug1 = boardSlugFixture("game")
            val slug2 = boardSlugFixture("game")
            val slug3 = boardSlugFixture("game")

            assertThat(slug1).isEqualTo(slug2)
            assertThat(slug1).isEqualTo(slug3)
            assertThat(slug1.hashCode()).isEqualTo(slug2.hashCode())
            assertThat(slug1.hashCode()).isEqualTo(slug3.hashCode())
        }

        @Test
        @DisplayName("타입이 BoardName 이면서 값이 다르면 동등하지 않다")
        fun test3() {
            val slug1 = boardSlugFixture("game")
            val slug2 = boardSlugFixture("music")
            val slug3 = boardSlugFixture("history")

            assertThat(slug1).isNotEqualTo(slug2)
            assertThat(slug1).isNotEqualTo(slug3)
        }

        @Test
        @DisplayName("타입이 null 이면 동등하지 않다")
        fun test4() {
            val slug = boardSlugFixture("game")
            val other = null

            assertThat(slug).isNotEqualTo(other)
        }

        @Test
        @DisplayName("타입이 BoardName 이 아니면 동등하지 않다")
        fun test5() {
            val slug = boardSlugFixture("game")
            val other = 1L

            assertThat(slug).isNotEqualTo(other)
        }
    }

    @Test
    @DisplayName("toString: 디버깅을 위한 문자열 반환")
    fun testToString() {
        val value = "game"
        val slug = boardSlugFixture(value)
        assertThat(slug.toString()).isEqualTo("BoardSlug(value=$value)")
    }
}
