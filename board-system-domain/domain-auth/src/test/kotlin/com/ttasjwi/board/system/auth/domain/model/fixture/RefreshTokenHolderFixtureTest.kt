package com.ttasjwi.board.system.auth.domain.model.fixture

import com.ttasjwi.board.system.member.domain.model.Role
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
        assertThat(refreshTokenHolder.authMember.memberId.value).isNotNull()
        assertThat(refreshTokenHolder.authMember.email.value).isNotNull()
        assertThat(refreshTokenHolder.authMember.username.value).isNotNull()
        assertThat(refreshTokenHolder.authMember.nickname.value).isNotNull()
        assertThat(refreshTokenHolder.authMember.role).isNotNull()
        assertThat(tokens).isEmpty()
    }

    @Test
    @DisplayName("커스텀 파라미터 생성 테스트")
    fun test2() {
        // given
        val memberId = 3L
        val email = "jetty1234@gmail.com"
        val username = "jetty1234"
        val nickname = "jetty"
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
            email = email,
            username = username,
            nickname = nickname,
            role = role,
            tokens = tokens,
        )
        val innerTokens = refreshTokenHolder.getTokens()

        // then
        assertThat(refreshTokenHolder.authMember.memberId.value).isEqualTo(memberId)
        assertThat(refreshTokenHolder.authMember.email.value).isEqualTo(email)
        assertThat(refreshTokenHolder.authMember.username.value).isEqualTo(username)
        assertThat(refreshTokenHolder.authMember.nickname.value).isEqualTo(nickname)
        assertThat(refreshTokenHolder.authMember.role).isEqualTo(role)
        assertThat(innerTokens).containsAllEntriesOf(tokens)
    }
}
