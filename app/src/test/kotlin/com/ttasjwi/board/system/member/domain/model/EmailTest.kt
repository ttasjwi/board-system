package com.ttasjwi.board.system.member.domain.model

import com.ttasjwi.board.system.member.domain.model.fixture.emailFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Email 테스트")
class EmailTest {

    @Test
    @DisplayName("restore: 이메일 값으로부터 Email 인스턴스를 복원한다.")
    fun testRestore() {
        val value = "hello@gmail.com"
        val email = Email.restore(value)
        assertThat(email.value).isEqualTo(value)
    }

    @Nested
    @DisplayName("equals & hashCode")
    inner class EqualsAndHashCode {

        @Test
        @DisplayName("참조가 같으면 동등하다.")
        fun sameReference() {
            val email = emailFixture("test@gmail.com")
            assertThat(email).isEqualTo(email)
            assertThat(email.hashCode()).isEqualTo(email.hashCode())
        }

        @Test
        @DisplayName("Email이면서 값이 같으면 동등하다")
        fun emailAndSameValue() {
            val email1 = emailFixture("test@gmail.com")
            val email2 = emailFixture("test@gmail.com")
            val email3 = emailFixture("test@gmail.com")

            assertThat(email1).isEqualTo(email2)
            assertThat(email1).isEqualTo(email3)
            assertThat(email1.hashCode()).isEqualTo(email2.hashCode())
            assertThat(email1.hashCode()).isEqualTo(email3.hashCode())
        }

        @Test
        @DisplayName("타입이 Email 이면서 값이 다르면 동등하지 않다")
        fun equalsAndHashCodeTest() {
            val email1 = emailFixture("test@gmail.com")
            val email2 = emailFixture("sest@gmail.com")
            val email3 = emailFixture("jest@gmail.com")

            assertThat(email1).isNotEqualTo(email2)
            assertThat(email1).isNotEqualTo(email3)
        }

        @Test
        @DisplayName("타입 null 이면 동등하지 않다")
        fun nullTest() {
            val email = emailFixture("test@gmail.com")
            val other = null

            assertThat(email).isNotEqualTo(other)
        }

        @Test
        @DisplayName("타입이 Email이 아니면 동등하지 않다")
        fun deferenceType() {
            val email = emailFixture("test@gmail.com")
            val other = 1L

            assertThat(email).isNotEqualTo(other)
        }
    }

    @Test
    @DisplayName("toString: 디버깅을 위한 문자열 반환")
    fun testToString() {
        val value = "hello@gmail.com"
        val email = emailFixture(value)
        assertThat(email.toString()).isEqualTo("Email(value=$value)")
    }
}
