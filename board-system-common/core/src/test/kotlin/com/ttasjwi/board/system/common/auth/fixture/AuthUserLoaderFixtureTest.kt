package com.ttasjwi.board.system.common.auth.fixture

import com.ttasjwi.board.system.common.auth.Role
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("AuthUserLoaderFixture 테스트")
class AuthUserLoaderFixtureTest {

    private lateinit var authUserLoaderFixture: AuthUserLoaderFixture

    @BeforeEach
    fun setup() {
        authUserLoaderFixture = AuthUserLoaderFixture()
    }

    @Test
    @DisplayName("현재 인증회원을 찾을 수 있으며, 기본값은 null 이다.")
    fun testDefault() {
        // given
        // when
        val authUser = authUserLoaderFixture.loadCurrentAuthUser()

        // then
        assertThat(authUser).isNull()
    }

    @Test
    @DisplayName("changeAuthUser 를 통해 AuthUser 를 변경할 수 있다.")
    fun testChangeAndLoad() {
        // given
        val authUser = authUserFixture(userId = 3L, role = Role.ADMIN)
        authUserLoaderFixture.changeAuthUser(authUser)

        // when
        val loadedAuthUser = authUserLoaderFixture.loadCurrentAuthUser()

        // then
        assertThat(loadedAuthUser).isEqualTo(authUser)
    }
}
