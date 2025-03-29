package com.ttasjwi.board.system

import com.ttasjwi.board.system.auth.application.usecase.fixture.SocialLoginUseCaseFixture
import com.ttasjwi.board.system.auth.domain.service.fixture.AccessTokenManagerFixture
import com.ttasjwi.board.system.common.locale.fixture.LocaleManagerFixture
import com.ttasjwi.board.system.common.message.fixture.MessageResolverFixture
import com.ttasjwi.board.system.common.time.fixture.TimeManagerFixture
import com.ttasjwi.board.system.support.mvc.ExceptionApiTestFilter
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean

@TestConfiguration
class TestConfiguration {

    @Bean
    fun messageResolverFixture(): MessageResolverFixture {
        return MessageResolverFixture()
    }

    @Bean
    fun localeManagerFixture(): LocaleManagerFixture {
        return LocaleManagerFixture()
    }

    @Bean
    fun accessTokenManagerFixture(): AccessTokenManagerFixture {
        return AccessTokenManagerFixture()
    }

    @Bean
    fun timeManagerFixture(): TimeManagerFixture {
        return TimeManagerFixture()
    }

    @Bean
    fun socialLoginUseCaseFixture(): SocialLoginUseCaseFixture {
        return SocialLoginUseCaseFixture()
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
