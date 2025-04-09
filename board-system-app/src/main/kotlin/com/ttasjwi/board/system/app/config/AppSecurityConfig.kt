package com.ttasjwi.board.system.app.config

import com.ttasjwi.board.system.common.websupport.auth.config.CoreSecurityDsl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.SecurityFilterChain



@Configuration
class AppSecurityConfig(
    private val coreSecurityDsl: CoreSecurityDsl
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        coreSecurityDsl.apply(http)
        http {
            authorizeHttpRequests {
                authorize(anyRequest, authenticated)
            }
        }
        return http.build()
    }
}
