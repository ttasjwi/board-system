package com.ttasjwi.board.system.common.auth

import com.ttasjwi.board.system.common.auth.fixture.refreshTokenFixture
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("RefreshToken 테스트")
class RefreshTokenTest {

    @Nested
    @DisplayName("create: 토큰 생성")
    inner class Create {


        @Test
        @DisplayName("전달한 값을 기반으로 토큰이 잘 생성되는 지 테스트")
        fun test() {
            val memberId = 1L
            val refreshTokenId = 12345L
            val tokenValue = "token"
            val issuedAt = appDateTimeFixture(dayOfMonth = 1)
            val expiresAt = appDateTimeFixture(dayOfMonth = 2)

            val refreshToken = RefreshToken.create(memberId, refreshTokenId, issuedAt, expiresAt, tokenValue)

            // then
            assertThat(refreshToken.memberId).isEqualTo(memberId)
            assertThat(refreshToken.refreshTokenId).isEqualTo(refreshTokenId)
            assertThat(refreshToken.tokenType).isEqualTo(RefreshToken.VALID_TOKEN_TYPE)
            assertThat(refreshToken.tokenValue).isEqualTo(tokenValue)
            assertThat(refreshToken.issuer).isEqualTo(RefreshToken.VALID_ISSUER)
            assertThat(refreshToken.issuedAt.toInstant()).isEqualTo(issuedAt.toInstant())
            assertThat(refreshToken.expiresAt.toInstant()).isEqualTo(expiresAt.toInstant())
        }
    }

    @Nested
    @DisplayName("restore: 값들로부터 복원")
    inner class Restore {

        @Test
        @DisplayName("전달된 값들을 기반으로 내부 값들이 잘 복원되는 지 테스트")
        fun test() {
            // given
            val memberId = 1L
            val refreshTokenId = 132314135L
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
    @DisplayName("isExpired : 만료됐는 지 여부 반환")
    inner class IsExpiredTest {


        @Test
        @DisplayName("현재시간이 만료시간 이전이면 만료되지 않았다.")
        fun notExpiredTest() {
            val refreshToken = refreshTokenFixture(
                issuedAt = appDateTimeFixture(dayOfMonth = 1, hour = 0),
                expiresAt = appDateTimeFixture(dayOfMonth = 2, hour = 0)
            )
            val currentTime = appDateTimeFixture(dayOfMonth = 1, hour = 13)

            val isExpired = refreshToken.isExpired(currentTime)

            assertThat(isExpired).isFalse()
        }


        @Test
        @DisplayName("현재시간이 만료시간과 같으면 만료됐다.")
        fun expiredTest1() {
            val refreshToken = refreshTokenFixture(
                issuedAt = appDateTimeFixture(dayOfMonth = 1, hour = 0),
                expiresAt = appDateTimeFixture(dayOfMonth = 2, hour = 0)
            )
            val currentTime = appDateTimeFixture(dayOfMonth = 2, hour = 0)

            val isExpired = refreshToken.isExpired(currentTime)

            assertThat(isExpired).isTrue()
        }


        @Test
        @DisplayName("현재시간이 만료시간 이후면 만료됐다.")
        fun expiredTest2() {
            val refreshToken = refreshTokenFixture(
                issuedAt = appDateTimeFixture(dayOfMonth = 1, hour = 0),
                expiresAt = appDateTimeFixture(dayOfMonth = 2, hour = 0)
            )
            val currentTime = appDateTimeFixture(dayOfMonth = 2, hour = 1)

            val isExpired = refreshToken.isExpired(currentTime)

            assertThat(isExpired).isTrue()
        }
    }

}
