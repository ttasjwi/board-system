package com.ttasjwi.board.system.common.websupport.test.config

import com.ttasjwi.board.system.common.websupport.auth.config.CoreSecurityDsl
import com.ttasjwi.board.system.common.websupport.auth.config.SecurityConfig
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.config.annotation.web.invoke

@TestConfiguration
@Import(SecurityConfig::class)
class TestSecurityConfig(
    private val coreSecurityDsl: CoreSecurityDsl,
) {

    @Bean
    fun testFilterChain(http: HttpSecurity): SecurityFilterChain {
        coreSecurityDsl.apply(http)
        http {
            authorizeHttpRequests {
                authorize(anyRequest, permitAll)
            }
        }
        return http.build()
    }
}
