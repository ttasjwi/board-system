package com.ttasjwi.board.system.common.auth.fixture

import com.ttasjwi.board.system.common.auth.RefreshToken
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class RefreshTokenPortFixtureTest {

    private lateinit var refreshTokenPortFixture: RefreshTokenPortFixture

    @BeforeEach
    fun setup() {
        refreshTokenPortFixture = RefreshTokenPortFixture()
    }

    @Nested
    @DisplayName("Generate : 리프레시 토큰을 생성한다")
    inner class Generate {

        @Test
        @DisplayName("Generate : 작동 테스트")
        fun test() {
            // given
            val userId = 144L
            val refreshTokenId = 1245L
            val issuedAt = appDateTimeFixture(dayOfMonth = 1)
            val expiresAt = appDateTimeFixture(dayOfMonth = 2)

            // when
            val refreshToken = refreshTokenPortFixture.generate(userId, refreshTokenId, issuedAt, expiresAt)

            println("RefreshToken Value: ${refreshToken.tokenValue}")

            // then
            assertThat(refreshToken.userId).isEqualTo(userId)
            assertThat(refreshToken.refreshTokenId).isEqualTo(refreshTokenId)
            assertThat(refreshToken.tokenType).isEqualTo(RefreshToken.VALID_TOKEN_TYPE)
            assertThat(refreshToken.tokenValue).isNotNull()
            assertThat(refreshToken.issuer).isEqualTo(RefreshToken.VALID_ISSUER)
            assertThat(refreshToken.issuedAt).isEqualTo(issuedAt)
            assertThat(refreshToken.expiresAt).isEqualTo(expiresAt)
        }
    }

    @Nested
    @DisplayName("parse: 문자열로부터 액세스 토큰을 파싱해, 액세스토큰 인스턴스를 생성한다.")
    inner class Parse {

        @Test
        @DisplayName("복원 성공 테스트")
        fun testSuccess() {
            // given
            val tokenValue =
                "144,1245,RefreshToken,BoardSystem,2025-01-01T00:00+09:00[Asia/Seoul],2025-01-02T00:00+09:00[Asia/Seoul]"
            // when
            val accessToken = refreshTokenPortFixture.parse(tokenValue)

            // then
            assertThat(accessToken.userId).isEqualTo(144L)
            assertThat(accessToken.refreshTokenId).isEqualTo(1245L)
            assertThat(accessToken.tokenType).isEqualTo(RefreshToken.VALID_TOKEN_TYPE)
            assertThat(accessToken.tokenValue).isEqualTo(tokenValue)
            assertThat(accessToken.issuer).isEqualTo(RefreshToken.VALID_ISSUER)
            assertThat(accessToken.issuedAt).isEqualTo(appDateTimeFixture(dayOfMonth = 1))
            assertThat(accessToken.expiresAt).isEqualTo(appDateTimeFixture(dayOfMonth = 2))
        }
    }
}
