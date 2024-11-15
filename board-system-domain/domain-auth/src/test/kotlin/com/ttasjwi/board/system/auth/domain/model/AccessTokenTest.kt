package com.ttasjwi.board.system.auth.domain.model

import com.ttasjwi.board.system.auth.domain.model.fixture.accessTokenFixture
import com.ttasjwi.board.system.core.time.fixture.timeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("AccessToken 테스트")
class AccessTokenTest {

    @Nested
    @DisplayName("restore: 값들로부터 복원")
    inner class Restore {

        @Test
        @DisplayName("전달된 값들이 내부 값으로 잘 복원되는 지 테스트")
        fun test() {
            // given
            val memberId = 1L
            val roleName = "USER"
            val tokenValue = "accessToken1"
            val issuedAt = timeFixture(minute = 0)
            val expiresAt = timeFixture(minute = 30)

            // when
            val accessToken = AccessToken.restore(
                memberId = memberId,
                roleName = roleName,
                tokenValue = tokenValue,
                issuedAt = issuedAt,
                expiresAt = expiresAt,
            )

            // then
            assertThat(accessToken.authMember.memberId.value).isEqualTo(memberId)
            assertThat(accessToken.authMember.role.name).isEqualTo(roleName)
            assertThat(accessToken.tokenValue).isEqualTo(tokenValue)
            assertThat(accessToken.issuedAt).isEqualTo(issuedAt)
            assertThat(accessToken.expiresAt).isEqualTo(expiresAt)
        }
    }


    @Nested
    @DisplayName("Equals & HashCode")
    inner class EqualsAndHashCode {

        @Test
        @DisplayName("값이 같으면 동등하다")
        fun testEquals() {
            // given
            val accessToken = accessTokenFixture()
            val other = accessTokenFixture()

            // when
            val equals = accessToken.equals(other)

            // then
            assertThat(equals).isTrue()
            assertThat(accessToken.hashCode()).isEqualTo(other.hashCode())
        }

        @Test
        @DisplayName("참조가 같으면 동등하다")
        fun testSameReference() {
            // given
            val accessToken = accessTokenFixture()
            val other = accessToken

            // when
            val equals = accessToken.equals(other)

            // then
            assertThat(equals).isTrue()
            assertThat(accessToken.hashCode()).isEqualTo(other.hashCode())
        }

        @Test
        @DisplayName("AccessToken 이 아니면 동등하지 않다")
        fun testDifferentType() {
            // given
            val accessToken = accessTokenFixture()
            val other = 1L

            // when
            val equals = accessToken.equals(other)

            // then
            assertThat(equals).isFalse()
        }


        @Test
        @DisplayName("비교대상이 null 이면 동등하지 않다")
        fun testNull() {
            // given
            val accessToken = accessTokenFixture()
            val other = null

            // when
            val equals = accessToken.equals(other)

            // then
            assertThat(equals).isFalse()
        }

        @Test
        @DisplayName("다른 AuthMember 이면 동등하지 않다")
        fun testDifferentAuthMember() {
            // given
            val accessToken = accessTokenFixture(memberId = 1L)
            val other = accessTokenFixture(memberId = 2L)

            // when
            val equals = accessToken.equals(other)

            // then
            assertThat(equals).isFalse()
        }

        @Test
        @DisplayName("다른 토큰값을 가지면 동등하지 않다")
        fun testDifferentTokenValue() {
            // given
            val accessToken = accessTokenFixture(tokenValue = "accessToken1")
            val other = accessTokenFixture(tokenValue = "accessToken2")

            // when
            val equals = accessToken.equals(other)

            // then
            assertThat(equals).isFalse()
        }

        @Test
        @DisplayName("발행시각이 다르면 동등하지 않다")
        fun testDifferentIssuedAt() {
            // given
            val accessToken = accessTokenFixture(issuedAt = timeFixture(minute = 3))
            val other = accessTokenFixture(issuedAt = timeFixture(minute = 5))

            // when
            val equals = accessToken.equals(other)

            // then
            assertThat(equals).isFalse()
        }

        @Test
        @DisplayName("만료시각이 다르면 동등하지 않다")
        fun testDifferentExpiresAt() {
            // given
            val accessToken = accessTokenFixture(expiresAt = timeFixture(minute = 3))
            val other = accessTokenFixture(expiresAt = timeFixture(minute = 5))

            // when
            val equals = accessToken.equals(other)

            // then
            assertThat(equals).isFalse()
        }
    }
}
