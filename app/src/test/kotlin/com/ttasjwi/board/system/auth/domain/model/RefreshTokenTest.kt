package com.ttasjwi.board.system.auth.domain.model

import com.ttasjwi.board.system.auth.domain.model.fixture.refreshTokenFixture
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("RefreshToken 테스트")
class RefreshTokenTest {

    @Nested
    @DisplayName("restore: 값들로부터 복원")
    inner class Restore {

        @Test
        @DisplayName("전달된 값들을 기반으로 내부 값들이 잘 복원되는 지 테스트")
        fun test() {
            // given
            val memberId = 1L
            val refreshTokenId = "refreshTokenId1"
            val tokenValue = "refreshToken1"
            val issuedAt = appDateTimeFixture(dayOfMonth = 1).toInstant()
            val expiresAt = appDateTimeFixture(dayOfMonth = 2).toInstant()

            // when
            val refreshToken = RefreshToken.restore(
                memberId = memberId,
                refreshTokenId = refreshTokenId,
                tokenValue = tokenValue,
                issuedAt = issuedAt,
                expiresAt = expiresAt,
            )

            // then
            assertThat(refreshToken.memberId).isEqualTo(memberId)
            assertThat(refreshToken.refreshTokenId).isEqualTo(refreshTokenId)
            assertThat(refreshToken.tokenValue).isEqualTo(tokenValue)
            assertThat(refreshToken.issuedAt.toInstant()).isEqualTo(issuedAt)
            assertThat(refreshToken.expiresAt.toInstant()).isEqualTo(expiresAt)
        }
    }


    @Nested
    @DisplayName("Equals & HashCode")
    inner class EqualsAndHashCode {

        @Test
        @DisplayName("값이 같으면 동등하다")
        fun testEquals() {
            // given
            val refreshToken = refreshTokenFixture()
            val other = refreshTokenFixture()

            // when
            val equals = refreshToken.equals(other)

            // then
            assertThat(equals).isTrue()
            assertThat(refreshToken.hashCode()).isEqualTo(other.hashCode())
        }

        @Test
        @DisplayName("참조가 같으면 동등하다")
        fun testSameReference() {
            // given
            val refreshToken = refreshTokenFixture()
            val other = refreshToken

            // when
            val equals = refreshToken.equals(other)

            // then
            assertThat(equals).isTrue()
            assertThat(refreshToken.hashCode()).isEqualTo(other.hashCode())
        }

        @Test
        @DisplayName("RefreshToken 이 아니면 동등하지 않다")
        fun testDifferentType() {
            // given
            val refreshToken = refreshTokenFixture()
            val other = 1L

            // when
            val equals = refreshToken.equals(other)

            // then
            assertThat(equals).isFalse()
        }


        @Test
        @DisplayName("비교대상이 null 이면 동등하지 않다")
        fun testNull() {
            // given
            val refreshToken = refreshTokenFixture()
            val other = null

            // when
            val equals = refreshToken.equals(other)

            // then
            assertThat(equals).isFalse()
        }

        @Test
        @DisplayName("다른 MemberId 를 가지면 동등하지 않다")
        fun testDifferentMemberId() {
            // given
            val refreshToken = refreshTokenFixture(memberId = 1L)
            val other = refreshTokenFixture(memberId = 2L)

            // when
            val equals = refreshToken.equals(other)

            // then
            assertThat(equals).isFalse()
        }

        @Test
        @DisplayName("다른 RefreshTokenId 를 가지면 동등하지 않다")
        fun testDifferentRefreshTokenId() {
            // given
            val refreshToken = refreshTokenFixture(refreshTokenId = "refreshTokenId1")
            val other = refreshTokenFixture(refreshTokenId = "refreshTokenId2")

            // when
            val equals = refreshToken.equals(other)

            // then
            assertThat(equals).isFalse()
        }

        @Test
        @DisplayName("다른 토큰값을 가지면 동등하지 않다")
        fun testDifferentTokenValue() {
            // given
            val refreshToken = refreshTokenFixture(tokenValue = "refreshToken1")
            val other = refreshTokenFixture(tokenValue = "refreshToken2")

            // when
            val equals = refreshToken.equals(other)

            // then
            assertThat(equals).isFalse()
        }

        @Test
        @DisplayName("발행시각이 다르면 동등하지 않다")
        fun testDifferentIssuedAt() {
            // given
            val refreshToken = refreshTokenFixture(issuedAt = appDateTimeFixture(minute = 3))
            val other = refreshTokenFixture(issuedAt = appDateTimeFixture(minute = 5))

            // when
            val equals = refreshToken.equals(other)

            // then
            assertThat(equals).isFalse()
        }

        @Test
        @DisplayName("만료시각이 다르면 동등하지 않다")
        fun testDifferentExpiresAt() {
            // given
            val refreshToken = refreshTokenFixture(expiresAt = appDateTimeFixture(minute = 3))
            val other = refreshTokenFixture(expiresAt = appDateTimeFixture(minute = 5))

            // when
            val equals = refreshToken.equals(other)

            // then
            assertThat(equals).isFalse()
        }
    }
}
