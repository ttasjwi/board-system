package com.ttasjwi.board.system.auth.domain.model.fixture

import com.ttasjwi.board.system.common.time.fixture.timeFixture
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
        assertThat(refreshToken.memberId).isNotNull()
        assertThat(refreshToken.refreshTokenId.value).isNotNull()
        assertThat(refreshToken.tokenValue).isNotNull()
        assertThat(refreshToken.issuedAt).isNotNull()
        assertThat(refreshToken.expiresAt).isNotNull()
    }

    @Test
    @DisplayName("커스텀 파라미터 생성 테스트")
    fun test2() {
        // given
        val memberId = 2L
        val refreshTokenId = "refreshToken2"
        val tokenValue = "tokentoken"
        val issuedAt = timeFixture(dayOfMonth = 1)
        val expiresAt = timeFixture(dayOfMonth = 2)

        // when
        val refreshToken = refreshTokenFixture(
            memberId = memberId,
            refreshTokenId = refreshTokenId,
            tokenValue = tokenValue,
            issuedAt = issuedAt,
            expiresAt = expiresAt,
        )

        // then
        assertThat(refreshToken.memberId).isEqualTo(memberId)
        assertThat(refreshToken.refreshTokenId.value).isEqualTo(refreshTokenId)
        assertThat(refreshToken.tokenValue).isEqualTo(tokenValue)
        assertThat(refreshToken.issuedAt).isEqualTo(issuedAt)
        assertThat(refreshToken.expiresAt).isEqualTo(expiresAt)
    }
}
