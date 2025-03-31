package com.ttasjwi.board.system.auth.domain.model

import com.ttasjwi.board.system.auth.domain.model.fixture.refreshTokenIdFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("RefreshTokenId 테스트")
class RefreshTokenIdTest {

    @Nested
    @DisplayName("restore: 값들로부터 복원")
    inner class Restore {

        @Test
        @DisplayName("restore: 값을 통해 복원하면 그 값을 가진다")
        fun test() {
            // given
            val value = "refreshTokenId1234"

            // when
            val refreshTokenId = RefreshTokenId.restore(value)

            // then
            assertThat(refreshTokenId.value).isEqualTo(value)
        }
    }


    @Nested
    @DisplayName("Equals & HashCode")
    inner class EqualsAndHashCode {

        @Test
        @DisplayName("값이 같으면 동등하다")
        fun testEquals() {
            // given
            val refreshTokenId = refreshTokenIdFixture()
            val other = refreshTokenIdFixture()

            // when
            val equals = refreshTokenId.equals(other)

            // then
            assertThat(equals).isTrue()
            assertThat(refreshTokenId.hashCode()).isEqualTo(other.hashCode())
        }

        @Test
        @DisplayName("참조가 같으면 동등하다")
        fun testSameReference() {
            // given
            val refreshTokenId = refreshTokenIdFixture()
            val other = refreshTokenId

            // when
            val equals = refreshTokenId.equals(other)

            // then
            assertThat(equals).isTrue()
            assertThat(refreshTokenId.hashCode()).isEqualTo(other.hashCode())
        }

        @Test
        @DisplayName("RefreshToken 이 아니면 동등하지 않다")
        fun testDifferentType() {
            // given
            val refreshTokenId = refreshTokenIdFixture()
            val other = 1L

            // when
            val equals = refreshTokenId.equals(other)

            // then
            assertThat(equals).isFalse()
        }


        @Test
        @DisplayName("비교대상이 null 이면 동등하지 않다")
        fun testNull() {
            // given
            val refreshTokenId = refreshTokenIdFixture()
            val other = null

            // when
            val equals = refreshTokenId.equals(other)

            // then
            assertThat(equals).isFalse()
        }

        @Test
        @DisplayName("값이 다르면 동등하지 않다")
        fun testDifferentValue() {
            // given
            val refreshTokenId = refreshTokenIdFixture("refreshTokenId1")
            val other = refreshTokenIdFixture("refreshTokenId2")

            // when
            val equals = refreshTokenId.equals(other)

            // then
            assertThat(equals).isFalse()
        }
    }
}
