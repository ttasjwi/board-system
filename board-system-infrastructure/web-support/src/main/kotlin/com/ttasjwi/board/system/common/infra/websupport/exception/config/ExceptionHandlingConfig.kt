package com.ttasjwi.board.system.common.infra.websupport.exception.config

import com.ttasjwi.board.system.common.infra.websupport.exception.filter.CustomExceptionHandleFilter
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.HandlerExceptionResolver

@Configuration
@ComponentScan(basePackages = ["com.ttasjwi.board.system.common.infra.websupport.exception"])
class ExceptionHandlingConfig {

    companion object {
        /**
         * 로케일 설정 필터 우선도는 -104 이므로 로케일이 설정되고 실행됨
         * 스프링 시큐리티 필터(DelegatingFilterProxy) 기본 순서는 -100 이므로 스프링 시큐리티보다 먼저 시행됨
         *
         * 로케일 설정 필터 -> 예외 처리 -> 스프링 시큐리티 필터체인
         */
        private const val EXCEPTION_HANDLE_FILTER_ORDER = -103
    }

    @Bean
    fun customExceptionHandleFilter(
        @Qualifier(value = "handlerExceptionResolver")
        exceptionResolver: HandlerExceptionResolver
    ): FilterRegistrationBean<CustomExceptionHandleFilter> {

        val registration = FilterRegistrationBean<CustomExceptionHandleFilter>()
        registration.filter = CustomExceptionHandleFilter(exceptionResolver)
        registration.order = EXCEPTION_HANDLE_FILTER_ORDER
        return registration
    }
}
