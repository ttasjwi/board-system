package com.ttasjwi.board.system.common.auth.fixture

import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("AccessToken 픽스쳐 테스트")
class AccessTokenFixtureTest {

    @Test
    @DisplayName("기본값 생성 테스트")
    fun test1() {
        // given
        // when
        val accessToken = accessTokenFixture()

        // then
        assertThat(accessToken.authUser.userId).isNotNull()
        assertThat(accessToken.authUser.role).isNotNull()
        assertThat(accessToken.tokenValue).isNotNull()
        assertThat(accessToken.issuedAt).isNotNull()
        assertThat(accessToken.expiresAt).isNotNull()
        assertThat(accessToken.authUser).isNotNull()
    }

    @Test
    @DisplayName("커스텀 파라미터 생성 테스트")
    fun test2() {
        // given
        val userId = 2L
        val role = Role.ADMIN
        val tokenValue = "tokentoken"
        val issuedAt = appDateTimeFixture(minute = 3)
        val expiresAt = appDateTimeFixture(minute = 33)

        // when
        val accessToken = accessTokenFixture(
            userId = userId,
            role = role,
            tokenValue = tokenValue,
            issuedAt = issuedAt,
            expiresAt = expiresAt,
        )

        // then
        assertThat(accessToken.authUser.userId).isEqualTo(userId)
        assertThat(accessToken.authUser.role).isEqualTo(role)
        assertThat(accessToken.tokenValue).isEqualTo(tokenValue)
        assertThat(accessToken.issuedAt).isEqualTo(issuedAt)
        assertThat(accessToken.expiresAt).isEqualTo(expiresAt)
    }
}
