package com.ttasjwi.board.system.auth.domain.model.fixture

import com.ttasjwi.board.system.core.time.fixture.timeFixture
import com.ttasjwi.board.system.member.domain.model.Role
import org.assertj.core.api.Assertions.*
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
        assertThat(accessToken.authMember.memberId.value).isNotNull()
        assertThat(accessToken.authMember.email.value).isNotNull()
        assertThat(accessToken.authMember.username.value).isNotNull()
        assertThat(accessToken.authMember.nickname.value).isNotNull()
        assertThat(accessToken.authMember.role).isNotNull()
        assertThat(accessToken.tokenValue).isNotNull()
        assertThat(accessToken.issuedAt).isNotNull()
        assertThat(accessToken.expiresAt).isNotNull()
        assertThat(accessToken.authMember).isNotNull()
    }

    @Test
    @DisplayName("커스텀 파라미터 생성 테스트")
    fun test2() {
        // given
        val memberId = 2L
        val email = "jest@test.com"
        val username = "jest"
        val nickname = "ttascat"
        val role = Role.ADMIN
        val tokenValue = "tokentoken"
        val issuedAt = timeFixture(minute = 3)
        val expiresAt = timeFixture(minute = 33)

        // when
        val accessToken = accessTokenFixture(
            memberId = memberId,
            email = email,
            username = username,
            nickname = nickname,
            role = role,
            tokenValue = tokenValue,
            issuedAt = issuedAt,
            expiresAt = expiresAt,
        )

        // then
        assertThat(accessToken.authMember.memberId.value).isEqualTo(memberId)
        assertThat(accessToken.authMember.email.value).isEqualTo(email)
        assertThat(accessToken.authMember.username.value).isEqualTo(username)
        assertThat(accessToken.authMember.nickname.value).isEqualTo(nickname)
        assertThat(accessToken.authMember.role).isEqualTo(role)
        assertThat(accessToken.tokenValue).isEqualTo(tokenValue)
        assertThat(accessToken.issuedAt).isEqualTo(issuedAt)
        assertThat(accessToken.expiresAt).isEqualTo(expiresAt)
    }
}