package com.ttasjwi.board.system.auth.domain.model

import com.ttasjwi.board.system.auth.domain.model.fixture.refreshTokenFixture
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
            val email = "jetty1234@gmail.com"
            val username = "jetty1234"
            val nickname = "jetty"
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
                email = email,
                username = username,
                nickname = nickname,
                roleName = roleName,
                tokens = tokens
            )
            val innerTokens = refreshTokenHolder.getTokens()

            // then
            assertThat(refreshTokenHolder.authMember.memberId.value).isEqualTo(memberId)
            assertThat(refreshTokenHolder.authMember.email.value).isEqualTo(email)
            assertThat(refreshTokenHolder.authMember.username.value).isEqualTo(username)
            assertThat(refreshTokenHolder.authMember.nickname.value).isEqualTo(nickname)
            assertThat(refreshTokenHolder.authMember.role.name).isEqualTo(roleName)
            assertThat(innerTokens).containsAllEntriesOf(tokens)
        }
    }
}
