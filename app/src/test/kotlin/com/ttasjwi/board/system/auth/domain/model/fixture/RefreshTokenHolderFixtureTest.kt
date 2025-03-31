package com.ttasjwi.board.system.auth.domain.model.fixture

import com.ttasjwi.board.system.common.auth.domain.model.Role
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("RefreshTokenHolder 픽스쳐 테스트")
class RefreshTokenHolderFixtureTest {

    @Test
    @DisplayName("기본값 생성 테스트")
    fun test1() {
        // given
        // when
        val refreshTokenHolder = refreshTokenHolderFixture()
        val tokens = refreshTokenHolder.getTokens()

        // then
        assertThat(refreshTokenHolder.authMember.memberId).isNotNull()
        assertThat(refreshTokenHolder.authMember.role).isNotNull()
        assertThat(tokens).isEmpty()
    }

    @Test
    @DisplayName("커스텀 파라미터 생성 테스트")
    fun test2() {
        // given
        val memberId = 3L
        val role = Role.USER

        val token1 = refreshTokenFixture(memberId, "refreshToken1", "refreshTokenValue1")
        val token2 = refreshTokenFixture(memberId, "refreshToken2", "refreshTokenValue2")

        val tokens = mutableMapOf(
            token1.refreshTokenId to token1,
            token2.refreshTokenId to token2
        )

        // when
        val refreshTokenHolder = refreshTokenHolderFixture(
            memberId = memberId,
            role = role,
            tokens = tokens,
        )
        val innerTokens = refreshTokenHolder.getTokens()

        // then
        assertThat(refreshTokenHolder.authMember.memberId).isEqualTo(memberId)
        assertThat(refreshTokenHolder.authMember.role).isEqualTo(role)
        assertThat(innerTokens).containsAllEntriesOf(tokens)
    }
}
