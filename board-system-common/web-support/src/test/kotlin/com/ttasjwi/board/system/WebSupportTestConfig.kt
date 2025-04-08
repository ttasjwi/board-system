package com.ttasjwi.board.system

import com.ttasjwi.board.system.common.time.fixture.TimeManagerFixture
import com.ttasjwi.board.system.common.websupport.auth.config.CoreSecurityDsl
import com.ttasjwi.board.system.common.websupport.auth.config.SecurityConfig
import com.ttasjwi.board.system.common.websupport.exception.config.ExceptionHandlingConfig
import com.ttasjwi.board.system.common.websupport.locale.config.LocaleConfig
import com.ttasjwi.board.system.common.websupport.message.config.MessageConfig
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@TestConfiguration
@Import(
    value = [
        SecurityConfig::class,
        ExceptionHandlingConfig::class,
        MessageConfig::class,
        LocaleConfig::class
    ]
)
class WebSupportTestConfig(
    private val coreSecurityDsl: CoreSecurityDsl,
) {

    @Bean
    fun timeManagerFixture(): TimeManagerFixture {
        return TimeManagerFixture()
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        coreSecurityDsl.apply(http)
        return http.build()
    }
}
