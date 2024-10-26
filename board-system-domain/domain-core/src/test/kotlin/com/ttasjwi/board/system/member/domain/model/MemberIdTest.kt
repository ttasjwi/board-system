package com.ttasjwi.board.system.member.domain.model

import com.ttasjwi.board.system.member.domain.model.fixture.memberIdFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("MemberId 테스트")
class MemberIdTest {

    @Nested
    @DisplayName("restore: 아이디 값으로부터 MemberId 인스턴스를 복원한다.")
    inner class Restore {

        @Test
        fun test() {
            val value = 155L
            val memberId = MemberId.restore(value)
            assertThat(memberId.value).isEqualTo(value)
        }
    }

    @Nested
    @DisplayName("equals & hashCode")
    inner class EqualsAndHashCode {

        @Test
        @DisplayName("참조가 같으면 동등하다.")
        fun sameReference() {
            val memberId = memberIdFixture(1L)
            assertThat(memberId).isEqualTo(memberId)
            assertThat(memberId.hashCode()).isEqualTo(memberId.hashCode())
        }

        @Test
        @DisplayName("MemberId 이면서 값이 같으면 동등하다")
        fun emailAndSameValue() {
            val memberId1 = memberIdFixture(1L)
            val memberId2 = memberIdFixture(1L)
            val memberId3 = memberIdFixture(1L)

            assertThat(memberId1).isEqualTo(memberId2)
            assertThat(memberId1).isEqualTo(memberId3)
            assertThat(memberId1.hashCode()).isEqualTo(memberId2.hashCode())
            assertThat(memberId1.hashCode()).isEqualTo(memberId3.hashCode())
        }

        @Test
        @DisplayName("타입이 MemberId 이면서 값이 다르면 동등하지 않다")
        fun equalsAndHashCodeTest() {
            val memberId1 = memberIdFixture(1L)
            val memberId2 = memberIdFixture(2L)
            val memberId3 = memberIdFixture(3L)

            assertThat(memberId1).isNotEqualTo(memberId2)
            assertThat(memberId1).isNotEqualTo(memberId3)
        }

        @Test
        @DisplayName("타입 null 이면 동등하지 않다")
        fun nullTest() {
            val memberId = memberIdFixture(3L)
            val other = null

            assertThat(memberId).isNotEqualTo(other)
        }

        @Test
        @DisplayName("타입이 MemberId 가 아니면 동등하지 않다")
        fun deferenceType() {
            val memberId = memberIdFixture(3L)
            val other = 1L

            assertThat(memberId).isNotEqualTo(other)
        }
    }

    @Nested
    @DisplayName("toString")
    inner class ToString {
        @Test
        @DisplayName("toString: 디버깅을 위한 문자열 반환")
        fun test() {
            val value = 144L
            val email = memberIdFixture(value)
            assertThat(email.toString()).isEqualTo("MemberId(value=$value)")
        }
    }
}
