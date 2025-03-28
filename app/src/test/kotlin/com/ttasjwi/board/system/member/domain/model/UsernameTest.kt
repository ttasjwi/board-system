package com.ttasjwi.board.system.member.domain.model

import com.ttasjwi.board.system.member.domain.model.fixture.usernameFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Username 테스트")
class UsernameTest {


    @Nested
    @DisplayName("restore: 사용자 아이디(username) 값으로부터 Username 인스턴스를 복원한다.")
    inner class Restore {

        @Test
        fun test() {
            val value = "ttasjwi"
            val username = Username.restore(value)
            assertThat(username.value).isEqualTo(value)
        }
    }


    @Nested
    @DisplayName("equals & hashCode")
    inner class EqualsAndHashCode {

        @Test
        @DisplayName("참조가 같으면 동등하다.")
        fun sameReference() {
            val username = usernameFixture("ttasjwi")
            assertThat(username).isEqualTo(username)
            assertThat(username.hashCode()).isEqualTo(username.hashCode())
        }

        @Test
        @DisplayName("Username 이면서 값이 같으면 동등하다")
        fun emailAndSameValue() {
            val username1 = usernameFixture("ttasjwi")
            val username2 = usernameFixture("ttasjwi")
            val username3 = Username.restore("ttasjwi")

            assertThat(username1).isEqualTo(username2)
            assertThat(username1).isEqualTo(username3)
            assertThat(username1.hashCode()).isEqualTo(username2.hashCode())
            assertThat(username1.hashCode()).isEqualTo(username3.hashCode())
        }

        @Test
        @DisplayName("타입이 Username 이면서 값이 다르면 동등하지 않다")
        fun equalsAndHashCodeTest() {
            val username1 = usernameFixture("ttasjwi")
            val username2 = usernameFixture("ttascat")
            val username3 = Username.restore("ttasdog")

            assertThat(username1).isNotEqualTo(username2)
            assertThat(username1).isNotEqualTo(username3)
        }

        @Test
        @DisplayName("타입 null 이면 동등하지 않다")
        fun nullTest() {
            val username = usernameFixture("ttasjwi")
            val other = null

            assertThat(username).isNotEqualTo(other)
        }

        @Test
        @DisplayName("타입이 Username 이 아니면 동등하지 않다")
        fun deferenceType() {
            val username = usernameFixture("ttasjwi")
            val other = 1L

            assertThat(username).isNotEqualTo(other)
        }
    }

    @Nested
    @DisplayName("toString")
    inner class ToString {
        @Test
        @DisplayName("toString: 디버깅을 위한 문자열 반환")
        fun test() {
            val value = "ttasjwi"
            val email = usernameFixture(value)
            assertThat(email.toString()).isEqualTo("Username(value=$value)")
        }
    }
}
