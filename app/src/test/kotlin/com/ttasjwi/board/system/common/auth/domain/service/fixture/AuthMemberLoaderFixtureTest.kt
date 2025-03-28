package com.ttasjwi.board.system.common.auth.domain.service.fixture

import com.ttasjwi.board.system.common.auth.domain.model.Role
import com.ttasjwi.board.system.common.auth.domain.model.fixture.authMemberFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("AuthMemberLoaderFixture 테스트")
class AuthMemberLoaderFixtureTest {

    private lateinit var authMemberLoaderFixture: AuthMemberLoaderFixture

    @BeforeEach
    fun setup() {
        authMemberLoaderFixture = AuthMemberLoaderFixture()
    }

    @Test
    @DisplayName("현재 인증회원을 찾을 수 있으며, 기본값은 null 이다.")
    fun testDefault() {
        // given
        // when
        val authMember = authMemberLoaderFixture.loadCurrentAuthMember()

        // then
        assertThat(authMember).isNull()
    }

    @Test
    @DisplayName("changeAuthMember 를 통해 AuthMember 를 변경할 수 있다.")
    fun testChangeAndLoad() {
        // given
        val authMember = authMemberFixture(memberId = 3L, role = Role.ADMIN)
        authMemberLoaderFixture.changeAuthMember(authMember)

        // when
        val loadedAuthMember = authMemberLoaderFixture.loadCurrentAuthMember()

        // then
        assertThat(loadedAuthMember).isEqualTo(authMember)
    }
}
