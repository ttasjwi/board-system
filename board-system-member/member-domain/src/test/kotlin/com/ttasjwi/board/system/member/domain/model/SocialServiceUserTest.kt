package com.ttasjwi.board.system.member.domain.model

import com.ttasjwi.board.system.member.domain.model.fixture.socialServiceUserFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("SocialServiceUser: 소셜서비스 사용자")
class SocialServiceUserTest {

    @Nested
    @DisplayName("restore: 소셜서비스 이름, 소셜서비스 사용자 아이디로부터 값을 복원한다.")
    inner class Restore {

        @Test
        @DisplayName("restore 를 통해 잘 복원되는 지 테스트")
        fun test() {
            // given
            val socialServiceName = "google"
            val socialServiceUserId = "a134567e4"

            // when
            val socialServiceUser = SocialServiceUser.restore(socialServiceName, socialServiceUserId)

            // then
            assertThat(socialServiceUser.socialService.name).isEqualToIgnoringCase(socialServiceName)
            assertThat(socialServiceUser.socialServiceUserId).isEqualTo(socialServiceUserId)
        }
    }

    @Nested
    @DisplayName("Equals & HashCode : 동등성 테스트")
    inner class EqualsAndHashCode {

        @Test
        @DisplayName("참조가 같으면 동등하다.")
        fun sameReference() {
            val socialServiceUser1 = socialServiceUserFixture(SocialService.NAVER, "provider123")
            val socialServiceUser2 = socialServiceUser1
            assertThat(socialServiceUser2).isEqualTo(socialServiceUser1)
            assertThat(socialServiceUser2.hashCode()).isEqualTo(socialServiceUser1.hashCode())
        }

        @Test
        @DisplayName("모든 필드 값이 동등하면 동등하다")
        fun sameTypeAndSameValue() {
            val socialServiceUser1 = socialServiceUserFixture(SocialService.NAVER, "provider123")
            val socialServiceUser2 = socialServiceUserFixture(SocialService.NAVER, "provider123")
            val socialServiceUser3 = SocialServiceUser.restore("Naver", "provider123")

            assertThat(socialServiceUser1).isEqualTo(socialServiceUser2)
            assertThat(socialServiceUser1).isEqualTo(socialServiceUser3)
            assertThat(socialServiceUser1.hashCode()).isEqualTo(socialServiceUser2.hashCode())
            assertThat(socialServiceUser1.hashCode()).isEqualTo(socialServiceUser3.hashCode())
        }

        @Test
        @DisplayName("타입이 같으면서 서비스 공급자가 다르면 동등하지 않다")
        fun sameTypeAndDifferentValue1() {
            val socialServiceUser1 = socialServiceUserFixture(SocialService.NAVER, "provider123")
            val socialServiceUser2 = socialServiceUserFixture(SocialService.GOOGLE, "provider123")

            assertThat(socialServiceUser1).isNotEqualTo(socialServiceUser2)
        }

        @Test
        @DisplayName("타입이 같으면서 서비스 공급자가 다르면 동등하지 않다")
        fun sameTypeAndDifferentValue2() {
            val socialServiceUser1 = socialServiceUserFixture(SocialService.NAVER, "provider123")
            val socialServiceUser2 = socialServiceUserFixture(SocialService.NAVER, "provider456")

            assertThat(socialServiceUser1).isNotEqualTo(socialServiceUser2)
        }

        @Test
        @DisplayName("타입 null 이면 동등하지 않다")
        fun nullTest() {
            val socialServiceUser = socialServiceUserFixture(SocialService.NAVER, "provider123")
            val other = null
            assertThat(socialServiceUser).isNotEqualTo(other)
        }

        @Test
        @DisplayName("타입이 다르면 동등하지 않다")
        fun deferenceType() {
            val socialServiceUser = socialServiceUserFixture(SocialService.NAVER, "provider123")
            val other = 1L
            assertThat(socialServiceUser).isNotEqualTo(other)
        }
    }

    @Nested
    @DisplayName("toString: 디버깅용 문자열")
    inner class ToString {

        @Test
        @DisplayName("의도한 대로 문자열이 잘 출력되는 지 테스트")
        fun test() {
            // given
            val socialServiceUser = socialServiceUserFixture(SocialService.NAVER, "provider123")

            // when
            val str = socialServiceUser.toString()

            // then
            assertThat(str).isEqualTo("SocialServiceUser(socialService=${socialServiceUser.socialService}, socialServiceUserId='${socialServiceUser.socialServiceUserId}')")
        }
    }
}
