package com.ttasjwi.board.system.common.auth.fixture

import com.ttasjwi.board.system.common.auth.RefreshToken
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("RefreshToken 픽스쳐 테스트")
class RefreshTokenFixtureTest {

    @Test
    @DisplayName("기본값 생성 테스트")
    fun test1() {
        // given
        // when
        val refreshToken = refreshTokenFixture()

        // then
        assertThat(refreshToken.userId).isNotNull()
        assertThat(refreshToken.refreshTokenId).isNotNull()
        assertThat(refreshToken.tokenType).isEqualTo(RefreshToken.VALID_TOKEN_TYPE)
        assertThat(refreshToken.tokenValue).isNotNull()
        assertThat(refreshToken.issuer).isEqualTo(RefreshToken.VALID_ISSUER)
        assertThat(refreshToken.issuedAt).isNotNull()
        assertThat(refreshToken.expiresAt).isNotNull()
    }

    @Test
    @DisplayName("커스텀 파라미터 생성 테스트")
    fun test2() {
        // given
        val userId = 2L
        val refreshTokenId = 234567L
        val tokenValue = "tokentoken"
        val issuedAt = appDateTimeFixture(dayOfMonth = 1)
        val expiresAt = appDateTimeFixture(dayOfMonth = 2)

        // when
        val refreshToken = refreshTokenFixture(
            userId = userId,
            refreshTokenId = refreshTokenId,
            tokenValue = tokenValue,
            issuedAt = issuedAt,
            expiresAt = expiresAt,
        )

        // then
        assertThat(refreshToken.userId).isEqualTo(userId)
        assertThat(refreshToken.refreshTokenId).isEqualTo(refreshTokenId)
        assertThat(refreshToken.tokenType).isEqualTo(RefreshToken.VALID_TOKEN_TYPE)
        assertThat(refreshToken.tokenValue).isEqualTo(tokenValue)
        assertThat(refreshToken.issuer).isEqualTo(RefreshToken.VALID_ISSUER)
        assertThat(refreshToken.issuedAt).isEqualTo(issuedAt)
        assertThat(refreshToken.expiresAt).isEqualTo(expiresAt)
    }
}
