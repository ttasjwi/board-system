package com.ttasjwi.board.system.common.infra.test.config

import com.ttasjwi.board.system.common.infra.test.support.filter.ExceptionHandleTestFilter
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean

@TestConfiguration
class TestFilterConfig {

    /**
     * CustomExceptionHandleFilter 뒤에서 예외를 던지기 위한 필터
     */
    @Bean
    fun testFilter(): FilterRegistrationBean<ExceptionHandleTestFilter> {
        val registration = FilterRegistrationBean<ExceptionHandleTestFilter>()
        registration.filter = ExceptionHandleTestFilter()

        // CustomExceptionHandleFilter (-103) 보다 뒤에 둠
        registration.order = -102
        return registration
    }
}
