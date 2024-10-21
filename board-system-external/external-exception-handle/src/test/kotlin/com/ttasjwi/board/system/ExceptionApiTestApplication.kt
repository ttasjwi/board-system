package com.ttasjwi.board.system

import com.ttasjwi.board.system.core.message.MessageResolver
import com.ttasjwi.board.system.core.message.fixture.MessageResolverFixture
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(TestConfig::class)
class ExceptionApiTestApplication

fun main(args: Array<String>) {
    runApplication<ExceptionApiTestApplication>(*args)
}

@Configuration
class TestConfig {

    @Bean
    fun messageResolverFixture(): MessageResolver {
        return MessageResolverFixture()
    }

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
}
