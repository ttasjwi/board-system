package com.ttasjwi.board.system

import com.ttasjwi.board.system.common.time.fixture.TimeManagerFixture
import com.ttasjwi.board.system.integration.support.ExceptionApiTestFilter
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary

@TestConfiguration
class TestConfiguration {

    /**
     * CustomExceptionHandleFilter 뒤에서 예외를 던지기 위한 필터
     */
    @Bean
    fun testFilter(): FilterRegistrationBean<ExceptionApiTestFilter> {
        val registration = FilterRegistrationBean<ExceptionApiTestFilter>()
        registration.filter = ExceptionApiTestFilter()

        // CustomExceptionHandleFilter (-103) 보다 뒤에 둠
        registration.order = -102
        return registration
    }

    @Primary
    @Bean
    fun timeManager(): TimeManagerFixture {
        return TimeManagerFixture()
    }
}
