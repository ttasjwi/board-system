package com.ttasjwi.board.system.member.domain.model

import com.ttasjwi.board.system.member.domain.model.fixture.socialConnectionIdFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("SocialConnectionId: 소셜연동의 고유 식별자")
class SocialConnectionIdTest {

    @Nested
    @DisplayName("restore: 값으로부터 아이디 복원")
    inner class Restore {

        @Test
        @DisplayName("값으로부터 SocialConnectionId 를 복원한다.")
        fun test() {
            // given
            val value = 356L

            // when
            val socialConnectionId = SocialConnectionId.restore(value)

            // then
            assertThat(socialConnectionId.value).isEqualTo(value)
        }
    }

    @Nested
    @DisplayName("ToString: 디버깅용 문자열")
    inner class ToString {

        @Test
        @DisplayName("toString 이 값을 의도한 대로 잘 출력하는 지 테스트")
        fun test() {
            // given
            val value = 137L
            val socialConnection = socialConnectionIdFixture(value)

            // when
            val str = socialConnection.toString()

            // then
            assertThat(str).isEqualTo("SocialConnectionId(value=$value)")
        }
    }


    @Nested
    @DisplayName("equals & hashCode : 동등성 테스트" )
    inner class EqualsAndHashCode {

        @Test
        @DisplayName("참조가 같으면 동등하다.")
        fun sameReference() {
            val socialConnectionId1 = socialConnectionIdFixture(1L)
            val socialConnectionId2 = socialConnectionId1
            assertThat(socialConnectionId2).isEqualTo(socialConnectionId1)
            assertThat(socialConnectionId2.hashCode()).isEqualTo(socialConnectionId1.hashCode())
        }

        @Test
        @DisplayName("SocialConnectionId 이면서 값이 같으면 동등하다")
        fun sameTypeAndSameValue() {
            val socialConnectionId1 = socialConnectionIdFixture(137L)
            val socialConnectionId2 = socialConnectionIdFixture(137L)
            val socialConnectionId3 = SocialConnectionId.restore(137L)

            assertThat(socialConnectionId1).isEqualTo(socialConnectionId2)
            assertThat(socialConnectionId1).isEqualTo(socialConnectionId3)
            assertThat(socialConnectionId1.hashCode()).isEqualTo(socialConnectionId2.hashCode())
            assertThat(socialConnectionId1.hashCode()).isEqualTo(socialConnectionId3.hashCode())
        }

        @Test
        @DisplayName("타입이 SocialConnectionId 이면서 값이 다르면 동등하지 않다")
        fun sameTypeAndDifferentValue() {
            val socialConnectionId1 = socialConnectionIdFixture(137L)
            val socialConnectionId2 = socialConnectionIdFixture(187L)
            val socialConnectionId3 = socialConnectionIdFixture(153L)

            assertThat(socialConnectionId1).isNotEqualTo(socialConnectionId2)
            assertThat(socialConnectionId1).isNotEqualTo(socialConnectionId3)
        }

        @Test
        @DisplayName("타입 null 이면 동등하지 않다")
        fun nullTest() {
            val socialConnectionId = socialConnectionIdFixture(1557L)
            val other = null
            assertThat(socialConnectionId).isNotEqualTo(other)
        }

        @Test
        @DisplayName("타입이 SocialConnectionId 가 아니면 동등하지 않다")
        fun deferenceType() {
            val socialConnectionId = socialConnectionIdFixture(137L)
            val other = 1L

            assertThat(socialConnectionId).isNotEqualTo(other)
        }
    }
}
