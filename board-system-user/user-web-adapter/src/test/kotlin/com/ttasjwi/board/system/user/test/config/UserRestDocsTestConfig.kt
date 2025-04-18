package com.ttasjwi.board.system.user.test.config

import com.ttasjwi.board.system.common.infra.websupport.auth.config.CoreSecurityDsl
import com.ttasjwi.board.system.user.infra.spring.security.oauth2.config.OAuth2SecurityConfig
import com.ttasjwi.board.system.user.infra.spring.security.oauth2.config.OAuth2SecurityDsl
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.SecurityFilterChain

@TestConfiguration
@Import(OAuth2SecurityConfig::class, TestOAuth2ComponentFixtureConfig::class)
class UserRestDocsTestConfig(
    private val coreSecurityDsl: CoreSecurityDsl,
    private val oAuth2SecurityDsl: OAuth2SecurityDsl,
) {

    @Bean
    fun testSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        coreSecurityDsl.apply(http)
        oAuth2SecurityDsl.apply(http)
        http {
            authorizeHttpRequests {
                authorize(anyRequest, permitAll)
            }
        }
        return http.build()
    }
}
