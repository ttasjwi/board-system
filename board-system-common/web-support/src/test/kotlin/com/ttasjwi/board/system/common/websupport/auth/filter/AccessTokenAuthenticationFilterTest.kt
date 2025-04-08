package com.ttasjwi.board.system.common.websupport.auth.filter

import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.auth.fixture.authMemberFixture
import com.ttasjwi.board.system.common.time.fixture.TimeManagerFixture
import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.common.token.AccessTokenExpiredException
import com.ttasjwi.board.system.common.token.AccessTokenParser
import com.ttasjwi.board.system.common.token.fixture.accessTokenFixture
import io.mockk.*
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.junit.jupiter.api.*
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder

@DisplayName("AccessTokenAuthenticationFilter 테스트")
class AccessTokenAuthenticationFilterTest {

    private lateinit var accessTokenAuthenticationFilter: AccessTokenAuthenticationFilter
    private lateinit var accessTokenParser: AccessTokenParser
    private lateinit var timeManagerFixture: TimeManagerFixture
    private lateinit var request: HttpServletRequest
    private lateinit var response: HttpServletResponse
    private lateinit var filterChain: FilterChain

    @BeforeEach
    fun setup() {
        accessTokenParser = mockk<AccessTokenParser>()
        timeManagerFixture = TimeManagerFixture()
        accessTokenAuthenticationFilter = AccessTokenAuthenticationFilter(
            accessTokenParser = accessTokenParser,
            timeManager = timeManagerFixture
        )
        SecurityContextHolder.clearContext()
        request = mockk<HttpServletRequest>()
        response = mockk<HttpServletResponse>()
        filterChain = mockk<FilterChain>()
        timeManagerFixture.changeCurrentTime(appDateTimeFixture())
    }

    @AfterEach
    fun teardown() {
        SecurityContextHolder.clearContext()
        timeManagerFixture.changeCurrentTime(appDateTimeFixture())
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
        verify(exactly = 0) { request.getHeader(HttpHeaders.AUTHORIZATION) }
        verify(exactly = 0) { accessTokenParser.parse(anyNullable<String>()) }
        verify(exactly = 1) { filterChain.doFilter(request, response) }
    }

    @Test
    @DisplayName("토큰값이 없다면 다음 필터로 통과시킨다")
    fun testNullTokenValue() {
        // given
        every { filterChain.doFilter(request, response) } just Runs
        every { request.getHeader(HttpHeaders.AUTHORIZATION) } returns null

        // when
        accessTokenAuthenticationFilter.doFilterInternal(request, response, filterChain)

        // then
        verify(exactly = 1) { request.getHeader(HttpHeaders.AUTHORIZATION) }
        verify(exactly = 0) { accessTokenParser.parse(anyNullable<String>()) }
        verify(exactly = 1) { filterChain.doFilter(request, response) }
    }

    @Test
    @DisplayName("인증 토큰을 지참했다면 인증을 거쳐야(액세스토큰 파싱) 통과된다.")
    fun testWithValidAccessToken() {
        // given
        val accessToken = accessTokenFixture(
            memberId = 1L,
            role = Role.USER,
            tokenValue = "tokenValue",
            issuedAt = appDateTimeFixture(minute = 0),
            expiresAt = appDateTimeFixture(minute = 30),
        )

        every { request.getHeader(HttpHeaders.AUTHORIZATION) } returns "Bearer ${accessToken.tokenValue}"
        every { filterChain.doFilter(request, response) } just Runs
        every { accessTokenParser.parse(accessToken.tokenValue) } returns accessToken

        val currentTime = appDateTimeFixture(minute = 13)
        timeManagerFixture.changeCurrentTime(currentTime)


        // when
        accessTokenAuthenticationFilter.doFilterInternal(request, response, filterChain)

        // then
        verify(exactly = 1) { request.getHeader(HttpHeaders.AUTHORIZATION) }
        verify(exactly = 1) { accessTokenParser.parse(accessToken.tokenValue) }
        verify(exactly = 1) { filterChain.doFilter(request, response) }
    }

    @Test
    @DisplayName("인증이 만료됐다면 통과되지 않는다.")
    fun testWithExpiredAccessToken() {
        // given
        val accessToken = accessTokenFixture(
            memberId = 1L,
            role = Role.USER,
            tokenValue = "tokenValue",
            issuedAt = appDateTimeFixture(minute = 0),
            expiresAt = appDateTimeFixture(minute = 30),
        )

        every { request.getHeader(HttpHeaders.AUTHORIZATION) } returns "Bearer ${accessToken.tokenValue}"
        every { filterChain.doFilter(request, response) } just Runs
        every { accessTokenParser.parse(accessToken.tokenValue) } returns accessToken

        val currentTime = appDateTimeFixture(minute = 35)
        timeManagerFixture.changeCurrentTime(currentTime)


        // when
        assertThrows<AccessTokenExpiredException> {
            accessTokenAuthenticationFilter.doFilterInternal(request, response, filterChain)
        }

        // then
        verify(exactly = 1) { request.getHeader(HttpHeaders.AUTHORIZATION) }
        verify(exactly = 1) { accessTokenParser.parse(accessToken.tokenValue) }
        verify(exactly = 0) { filterChain.doFilter(request, response) }
    }

}
