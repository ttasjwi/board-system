package com.ttasjwi.board.system.member.domain.model

import com.ttasjwi.board.system.member.domain.model.fixture.nicknameFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Nickname 테스트")
class NicknameTest {

    @Nested
    @DisplayName("restore: 닉네임 값으로부터 Nickname 인스턴스를 복원한다.")
    inner class Restore {

        @Test
        fun test() {
            val value = "땃쥐"
            val nickname = Nickname.restore(value)
            assertThat(nickname.value).isEqualTo(value)
        }
    }

    @Nested
    @DisplayName("equals & hashCode")
    inner class EqualsAndHashCode {

        @Test
        @DisplayName("참조가 같으면 동등하다.")
        fun sameReference() {
            val nickname = nicknameFixture("땃쥐")
            assertThat(nickname).isEqualTo(nickname)
            assertThat(nickname.hashCode()).isEqualTo(nickname.hashCode())
        }

        @Test
        @DisplayName("Nickname 이면서 값이 같으면 동등하다")
        fun emailAndSameValue() {
            val nickname1 = nicknameFixture("땃쥐")
            val nickname2 = nicknameFixture("땃쥐")
            val nickname3 = nicknameFixture("땃쥐")
            assertThat(nickname1).isEqualTo(nickname2)
            assertThat(nickname1).isEqualTo(nickname3)
            assertThat(nickname1.hashCode()).isEqualTo(nickname2.hashCode())
            assertThat(nickname1.hashCode()).isEqualTo(nickname3.hashCode())
        }

        @Test
        @DisplayName("타입이 Nickname 이면서 값이 다르면 동등하지 않다")
        fun equalsAndHashCodeTest() {
            val nickname1 = nicknameFixture("땃쥐")
            val nickname2 = nicknameFixture("땃고양이")
            val nickname3 = Nickname.restore("땃개")

            assertThat(nickname1).isNotEqualTo(nickname2)
            assertThat(nickname1).isNotEqualTo(nickname3)
        }

        @Test
        @DisplayName("타입 null 이면 동등하지 않다")
        fun nullTest() {
            val nickname = nicknameFixture("땃쥐")
            val other = null

            assertThat(nickname).isNotEqualTo(other)
        }

        @Test
        @DisplayName("타입이 Nickname 이 아니면 동등하지 않다")
        fun deferenceType() {
            val nickname = nicknameFixture("땃쥐")
            val other = 1L

            assertThat(nickname).isNotEqualTo(other)
        }
    }

    @Nested
    @DisplayName("toString")
    inner class ToString {
        @Test
        @DisplayName("toString: 디버깅을 위한 문자열 반환")
        fun test() {
            val value = "땃쥐"
            val email = nicknameFixture(value)
            assertThat(email.toString()).isEqualTo("Nickname(value=$value)")
        }
    }
}
