package com.ttasjwi.board.system.core.config

import com.ttasjwi.board.system.core.locale.CustomLocaleContextFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.LocaleResolver

@Configuration
class LocaleConfig {

    companion object {

        /**
         * 스프링 자동구성에 등록된 RequestContextFilter 의 순서가 -105 로 되어있으므로 이 보다 순서를 더 뒤에 실행되도록 함
         * RequestContextFilter : -105
         * 스프링 시큐리티 필터(DelegatingFilterProxy) 기본 순서는 -100 이므로 로케일 설정이 스프링 시큐리티보다 먼저 시행됨
         */
        private const val LOCALE_FILTER_ORDER = -104
    }

    @Bean
    fun customFilterRegistration(localeResolver: LocaleResolver): FilterRegistrationBean<CustomLocaleContextFilter> {
        val registration = FilterRegistrationBean<CustomLocaleContextFilter>()
        registration.filter = CustomLocaleContextFilter(localeResolver)
        registration.order = LOCALE_FILTER_ORDER

        return registration
    }
}
