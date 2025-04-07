package com.ttasjwi.board.system.domain.auth.model

import com.ttasjwi.board.system.domain.auth.model.fixture.refreshTokenFixture
import com.ttasjwi.board.system.domain.auth.model.fixture.refreshTokenHolderFixture
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("RefreshTokenHolder 테스트")
class RefreshTokenHolderTest {

    @Nested
    @DisplayName("restore: 값들로부터 복원")
    inner class Restore {

        @Test
        @DisplayName("값을 전달하여 잘 복원하는 지 테스트")
        fun test() {
            // given
            val memberId = 3L
            val roleName = "USER"
            val token1 = refreshTokenFixture(memberId, "refreshToken1", "refreshTokenValue1")
            val token2 = refreshTokenFixture(memberId, "refreshToken2", "refreshTokenValue2")

            val tokens = mutableMapOf(
                token1.refreshTokenId to token1,
                token2.refreshTokenId to token2
            )

            // when
            val refreshTokenHolder = RefreshTokenHolder.restore(
                memberId = memberId,
                roleName = roleName,
                tokens = tokens
            )
            val innerTokens = refreshTokenHolder.getTokens()

            // then
            assertThat(refreshTokenHolder.authMember.memberId).isEqualTo(memberId)
            assertThat(refreshTokenHolder.authMember.role.name).isEqualTo(roleName)
            assertThat(innerTokens).containsAllEntriesOf(tokens)
        }
    }

    @Nested
    @DisplayName("expiresAt: 리프레시토큰 홀더의 만료시간을 반환한다")
    inner class ExpiresAt {

        @Test
        @DisplayName("토큰이 없으면 지금이 만료시점이다.")
        fun testEmpty() {
            // given
            val holder = refreshTokenHolderFixture(memberId = 1L)
            val currentTime = appDateTimeFixture(minute = 0)

            // when
            val expiresAt = holder.expiresAt(currentTime)

            // then
            assertThat(expiresAt).isEqualTo(currentTime)
        }

        @Test
        @DisplayName("토큰이 하나 있다면 토큰의 만료시점이 만료시점이다.")
        fun testNotEmpty1() {
            // given
            val refreshToken1 = refreshTokenFixture(
                memberId = 1L, refreshTokenId = "tokenId1",
                issuedAt = appDateTimeFixture(minute = 0), expiresAt = appDateTimeFixture(minute = 5)
            )

            val holder = refreshTokenHolderFixture(
                memberId = 1L,
                tokens = mutableMapOf(
                    refreshToken1.refreshTokenId to refreshToken1,
                )
            )
            val currentTime = appDateTimeFixture(minute = 3)

            // when
            val expiresAt = holder.expiresAt(currentTime)

            assertThat(expiresAt).isEqualTo(refreshToken1.expiresAt)
        }

        @Test
        @DisplayName("토큰이 여러 개 있다면 가장 만료시간이 최신(최대)인 토큰의 만료시점이 만료시점이다.")
        fun testNotEmpty2() {
            // given
            val refreshToken1 = refreshTokenFixture(
                memberId = 1L, refreshTokenId = "tokenId1",
                issuedAt = appDateTimeFixture(minute = 0), expiresAt = appDateTimeFixture(minute = 5)
            )
            val refreshToken2 = refreshTokenFixture(
                memberId = 1L, refreshTokenId = "tokenId2",
                issuedAt = appDateTimeFixture(minute = 2), expiresAt = appDateTimeFixture(minute = 7)
            )
            val refreshToken3 = refreshTokenFixture(
                memberId = 1L, refreshTokenId = "tokenId3",
                issuedAt = appDateTimeFixture(minute = 1), expiresAt = appDateTimeFixture(minute = 6)
            )

            val holder = refreshTokenHolderFixture(
                memberId = 1L,
                tokens = mutableMapOf(
                    refreshToken1.refreshTokenId to refreshToken1,
                    refreshToken2.refreshTokenId to refreshToken2,
                    refreshToken3.refreshTokenId to refreshToken3
                )
            )
            val currentTime = appDateTimeFixture(minute = 3)

            // when
            val expiresAt = holder.expiresAt(currentTime)

            assertThat(expiresAt).isEqualTo(refreshToken2.expiresAt)
        }
    }
}
