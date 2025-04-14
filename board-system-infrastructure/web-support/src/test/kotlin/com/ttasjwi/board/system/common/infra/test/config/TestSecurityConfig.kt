package com.ttasjwi.board.system.common.infra.test.config

import com.ttasjwi.board.system.common.infra.websupport.auth.config.CoreSecurityConfig
import com.ttasjwi.board.system.common.infra.websupport.auth.config.CoreSecurityDsl
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.SecurityFilterChain

@TestConfiguration
@Import(CoreSecurityConfig::class)
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
