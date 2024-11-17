package com.ttasjwi.board.system.auth.domain.external

import com.ttasjwi.board.system.auth.domain.model.fixture.authMemberFixture
import com.ttasjwi.board.system.auth.domain.service.AuthMemberLoader
import com.ttasjwi.board.system.external.spring.security.authentication.AuthMemberAuthentication
import com.ttasjwi.board.system.member.domain.model.Role
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.context.SecurityContextHolder

@DisplayName("AuthMemberLoaderImpl 테스트")
class AuthMemberLoaderImplTest {

    private lateinit var authMemberLoader: AuthMemberLoader

    @BeforeEach
    fun setup() {
        authMemberLoader = AuthMemberLoaderImpl()
    }

    @Test
    @DisplayName("시큐리티 컨텍스트에 AuthMemberAuthentication 이 있으면, AuthMember 가 반환된다.")
    fun testAuthMember() {
        // given
        val authMember = authMemberFixture(memberId = 1235L, role = Role.USER)

        val securityContext = SecurityContextHolder.getContextHolderStrategy().createEmptyContext()
        securityContext.authentication = AuthMemberAuthentication.from(authMember)
        SecurityContextHolder.getContextHolderStrategy().context = securityContext

        // when
        val loadedAuthMember = authMemberLoader.loadCurrentAuthMember()

        // then
        assertThat(loadedAuthMember).isEqualTo(authMember)
    }

    @Test
    @DisplayName("시큐리티 컨텍스트가 비어있을 경우 Null 이 반환된다")
    fun testNull() {
        // given
        SecurityContextHolder.getContextHolderStrategy().clearContext()

        // when
        val loadedAuthMember = authMemberLoader.loadCurrentAuthMember()

        // then
        assertThat(loadedAuthMember).isNull()
    }

    @Test
    @DisplayName("AuthMemberAuthentication 이 아닐 경우, null 이 반환된다")
    fun testAnonymous() {
        // given
        SecurityContextHolder.getContext().authentication = AnonymousAuthenticationToken("hello", "anonymousUser", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"))

        // when
        val loadedAuthMember = authMemberLoader.loadCurrentAuthMember()

        // then
        assertThat(loadedAuthMember).isNull()
    }
}
