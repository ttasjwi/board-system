package com.ttasjwi.board.system.common.token

import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.auth.fixture.authMemberFixture
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.common.token.fixture.accessTokenFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*

@DisplayName("AccessToken 테스트")
class AccessTokenTest {

    @Nested
    @DisplayName("create : 최초 생성")
    inner class Create {

        @Test
        @DisplayName("전달된 값을 기반으로 잘 생성되는 지 테스트")
        fun test() {
            // given
            val memberId = 1L
            val role = Role.ADMIN
            val authMember = authMemberFixture(memberId = memberId, role = role)
            val tokenValue = "tokenValue"
            val issuedAt = appDateTimeFixture(minute = 10)
            val expiresAt = appDateTimeFixture(minute = 40)

            // when
            val accessToken = AccessToken.create(
                authMember = authMember,
                tokenValue = tokenValue,
                issuedAt = issuedAt,
                expiresAt = expiresAt
            )

            // then
            assertThat(accessToken.authMember.memberId).isEqualTo(memberId)
            assertThat(accessToken.authMember.role).isEqualTo(role)
            assertThat(accessToken.tokenType).isEqualTo(AccessToken.VALID_TOKEN_TYPE)
            assertThat(accessToken.tokenValue).isEqualTo(tokenValue)
            assertThat(accessToken.issuer).isEqualTo(AccessToken.VALID_ISSUER)
            assertThat(accessToken.issuedAt).isEqualTo(issuedAt)
            assertThat(accessToken.expiresAt).isEqualTo(expiresAt)
        }
    }

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
            val issuedAt = appDateTimeFixture(minute = 0).toInstant()
            val expiresAt = appDateTimeFixture(minute = 30).toInstant()

            // when
            val accessToken = AccessToken.restore(
                memberId = memberId,
                roleName = roleName,
                tokenValue = tokenValue,
                issuedAt = issuedAt,
                expiresAt = expiresAt,
            )

            // then
            assertThat(accessToken.authMember.memberId).isEqualTo(memberId)
            assertThat(accessToken.authMember.role.name).isEqualTo(roleName)
            assertThat(accessToken.tokenType).isEqualTo(AccessToken.VALID_TOKEN_TYPE)
            assertThat(accessToken.tokenValue).isEqualTo(tokenValue)
            assertThat(accessToken.issuer).isEqualTo(AccessToken.VALID_ISSUER)
            assertThat(accessToken.issuedAt.toInstant()).isEqualTo(issuedAt)
            assertThat(accessToken.expiresAt.toInstant()).isEqualTo(expiresAt)
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
            val accessToken = accessTokenFixture(issuedAt = appDateTimeFixture(minute = 3))
            val other = accessTokenFixture(issuedAt = appDateTimeFixture(minute = 5))

            // when
            val equals = accessToken.equals(other)

            // then
            assertThat(equals).isFalse()
        }

        @Test
        @DisplayName("만료시각이 다르면 동등하지 않다")
        fun testDifferentExpiresAt() {
            // given
            val accessToken = accessTokenFixture(expiresAt = appDateTimeFixture(minute = 3))
            val other = accessTokenFixture(expiresAt = appDateTimeFixture(minute = 5))

            // when
            val equals = accessToken.equals(other)

            // then
            assertThat(equals).isFalse()
        }
    }

    @Nested
    @DisplayName("throwIfExpired: 만료됐는 지 확인하고, 만료됐다면 예외를 발생시킨다.")
    inner class ThrowIfExpired {


        @Test
        @DisplayName("현재 시간이 만료시간 이전이면 예외가 발생하지 않는다.")
        fun testNotExpired() {
            // given
            val accessToken = accessTokenFixture(
                issuedAt = appDateTimeFixture(minute = 3),
                expiresAt = appDateTimeFixture(minute = 33),
            )
            val currentTime = appDateTimeFixture(minute = 28)

            // when
            // then
            assertDoesNotThrow { accessToken.throwIfExpired(currentTime) }
        }


        @Test
        @DisplayName("현재 시간이 만료시간과 같으면 예외가 발생한다.")
        fun testExpired1() {
            // given
            val accessToken = accessTokenFixture(
                issuedAt = appDateTimeFixture(minute = 3),
                expiresAt = appDateTimeFixture(minute = 33),
            )
            val currentTime = accessToken.expiresAt

            // when
            assertThrows<AccessTokenExpiredException> { accessToken.throwIfExpired(currentTime) }
        }


        @Test
        @DisplayName("현재 시간이 만료시간 이후면 예외가 발생한다.")
        fun testExpired2() {
            // given
            val accessToken = accessTokenFixture(
                issuedAt = appDateTimeFixture(minute = 3),
                expiresAt = appDateTimeFixture(minute = 33),
            )
            val currentTime = accessToken.expiresAt.plusSeconds(1)

            // when
            assertThrows<AccessTokenExpiredException> { accessToken.throwIfExpired(currentTime) }
        }
    }
}
