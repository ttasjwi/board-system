package com.ttasjwi.board.system.spring.security.authentication

import com.ttasjwi.board.system.common.auth.domain.model.Role
import com.ttasjwi.board.system.common.auth.domain.model.fixture.authMemberFixture
import com.ttasjwi.board.system.common.time.TimeManager
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.domain.auth.model.fixture.accessTokenFixture
import com.ttasjwi.board.system.domain.auth.service.AccessTokenManager
import com.ttasjwi.board.system.spring.security.support.BearerTokenResolver
import io.mockk.*
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder

@DisplayName("AccessTokenAuthenticationFilter 테스트")
class AccessTokenAuthenticationFilterTest {

    private lateinit var accessTokenAuthenticationFilter: AccessTokenAuthenticationFilter
    private lateinit var bearerTokenResolver: BearerTokenResolver
    private lateinit var accessTokenManager: AccessTokenManager
    private lateinit var timeManager: TimeManager
    private lateinit var request: HttpServletRequest
    private lateinit var response: HttpServletResponse
    private lateinit var filterChain: FilterChain

    @BeforeEach
    fun setup() {
        bearerTokenResolver = mockk()
        accessTokenManager = mockk()
        timeManager = mockk()
        accessTokenAuthenticationFilter = AccessTokenAuthenticationFilter(
            bearerTokenResolver = bearerTokenResolver,
            accessTokenManager = accessTokenManager,
            timeManager = timeManager
        )
        SecurityContextHolder.clearContext()
        request = mockk<HttpServletRequest>()
        response = mockk<HttpServletResponse>()
        filterChain = mockk<FilterChain>()
    }

    @AfterEach
    fun teardown() {
        SecurityContextHolder.clearContext()
    }

    @Test
    @DisplayName("이미 인증된 회원은 필터를 통과시킨다.")
    fun testAuthenticated() {
        // given
        SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken.authenticated(
            authMemberFixture(1L, Role.USER),
            "",
            mutableListOf(SimpleGrantedAuthority("ROLE_USER"))
        )

        every { filterChain.doFilter(request, response) } just Runs

        // when
        accessTokenAuthenticationFilter.doFilterInternal(request, response, filterChain)

        // then
        verify(exactly = 1) { filterChain.doFilter(request, response) }
        verify(exactly = 0) { bearerTokenResolver.resolve(request) }
    }

    @Test
    @DisplayName("토큰값이 없다면 다음 필터로 통과시킨다")
    fun testNullTokenValue() {
        // given
        every { bearerTokenResolver.resolve(request) } returns null
        every { filterChain.doFilter(request, response) } just Runs

        // when
        accessTokenAuthenticationFilter.doFilterInternal(request, response, filterChain)

        // then
        verify(exactly = 1) { bearerTokenResolver.resolve(request) }
        verify(exactly = 1) { filterChain.doFilter(request, response) }
        verify(exactly = 0) { accessTokenManager.parse(anyNullable()) }
    }

    @Test
    @DisplayName("인증 토큰을 지참했다면 인증을 해야 통과된다.")
    fun testWithValidAccessToken() {
        // given
        val tokenValue = "validToken"
        val accessToken = accessTokenFixture(
            memberId = 1557L, role = Role.ADMIN, tokenValue = tokenValue,
            issuedAt = appDateTimeFixture(minute = 5), expiresAt = appDateTimeFixture(minute = 35)
        )
        val currentTime = appDateTimeFixture(minute = 13)

        every { bearerTokenResolver.resolve(request) } returns tokenValue
        every { accessTokenManager.parse(tokenValue) } returns accessToken
        every { timeManager.now() } returns currentTime
        every { accessTokenManager.checkCurrentlyValid(accessToken, currentTime) } just Runs
        every { filterChain.doFilter(request, response) } just Runs

        // when
        accessTokenAuthenticationFilter.doFilterInternal(request, response, filterChain)

        // then
        verify(exactly = 1) { accessTokenManager.parse(tokenValue) }
        verify(exactly = 1) { bearerTokenResolver.resolve(request) }
        verify(exactly = 1) { timeManager.now() }
        verify(exactly = 1) { accessTokenManager.checkCurrentlyValid(accessToken, currentTime) }
        verify(exactly = 1) { filterChain.doFilter(request, response) }
    }
}
