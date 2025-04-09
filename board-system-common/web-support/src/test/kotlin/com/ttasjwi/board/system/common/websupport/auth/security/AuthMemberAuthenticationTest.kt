package com.ttasjwi.board.system.common.websupport.auth.security

import com.ttasjwi.board.system.common.auth.AuthMember
import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.auth.fixture.authMemberFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority

@DisplayName("AuthMemberAuthentication: AuthMember를 통해 얻어낸 Spring Security Authentication")
class AuthMemberAuthenticationTest {

    private lateinit var authMember: AuthMember
    private lateinit var authentication: Authentication

    @BeforeEach
    fun setup() {
        authMember = authMemberFixture(memberId = 1357L, role = Role.ADMIN)
        authentication = AuthMemberAuthentication.from(authMember)
    }

    @Test
    @DisplayName("getName: Null 반환")
    fun testGetName() {
        // given
        // when
        val name = authentication.name

        // then
        assertThat(name).isNull()
    }

    @Test
    @DisplayName("getAuthorities: 권한목록 반환")
    fun testGetAuthorities() {
        // given
        // when
        val authorities = authentication.authorities

        // then
        assertThat(authorities).containsExactly(SimpleGrantedAuthority("ROLE_ADMIN"))
    }

    @Test
    @DisplayName("getCredentials: Null 반환")
    fun testGetCredentials() {
        // given
        // when
        val credentials = authentication.credentials

        // then
        assertThat(credentials).isNull()
    }

    @Test
    @DisplayName("getDetails: Null 반환")
    fun testGetDetails() {
        // given
        // when
        val details = authentication.details

        // then
        assertThat(details).isNull()
    }

    @Test
    @DisplayName("getPrincipal: authMember 반환")
    fun testGetPrincipal() {
        // given
        // when
        val innerPrincipal = authentication.principal as AuthMember

        // then
        assertThat(innerPrincipal).isEqualTo(authMember)
    }


    @Test
    @DisplayName("isAuthenticated: true 반환")
    fun testIsAuthenticated() {
        // given
        // when
        val authenticated = authentication.isAuthenticated

        // then
        assertThat(authenticated).isTrue()
    }


    @Test
    @DisplayName("setAuthenticated: 예외 발생")
    fun testSetAuthenticated() {
        // given
        // when
        // then
        assertThrows<IllegalStateException> { authentication.isAuthenticated = true }
            .let {
                assertThat(it.message).isEqualTo("cannot set this token to trusted")
            }
    }
}
