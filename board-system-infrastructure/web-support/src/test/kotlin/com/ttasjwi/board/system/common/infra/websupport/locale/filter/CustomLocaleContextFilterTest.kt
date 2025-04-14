package com.ttasjwi.board.system.common.infra.websupport.locale.filter

import io.mockk.*
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.web.servlet.LocaleResolver
import java.util.*

@DisplayName("CustomLocaleContextFilter: 필터들 앞에서, 로케일을 LocaleContextHolder 에 저장")
class CustomLocaleContextFilterTest {

    private lateinit var filter: CustomLocaleContextFilter
    private lateinit var localeResolver: LocaleResolver
    private lateinit var request: HttpServletRequest
    private lateinit var response: HttpServletResponse
    private lateinit var filterChain: FilterChain

    @BeforeEach
    fun setup() {
        localeResolver = mockk<LocaleResolver>()
        request = mockk<HttpServletRequest>()
        response = mockk<HttpServletResponse>()
        filterChain = mockk<FilterChain>()
        filter = CustomLocaleContextFilter(
            localeResolver = localeResolver,
        )
    }

    @Test
    @DisplayName("Spring LocaleResolver 를 통해 로케일을 얻어온 뒤 로케일 컨텍스트 홀더에 셋팅한다.")
    fun test() {
        every { localeResolver.resolveLocale(request) } returns Locale.KOREAN
        every { filterChain.doFilter(request, response) } just Runs

        filter.doFilterInternal(request, response, filterChain)

        verify(exactly = 1) { localeResolver.resolveLocale(request) }
        verify(exactly = 1) { filterChain.doFilter(request, response) }
    }
}
