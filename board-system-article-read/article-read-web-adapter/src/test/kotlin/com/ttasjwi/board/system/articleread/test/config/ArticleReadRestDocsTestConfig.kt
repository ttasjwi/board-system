package com.ttasjwi.board.system.articleread.test.config

import com.ttasjwi.board.system.common.infra.websupport.auth.config.CoreSecurityDsl
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.SecurityFilterChain

@TestConfiguration
class ArticleReadRestDocsTestConfig(
    private val coreSecurityDsl: CoreSecurityDsl
) {

    @Bean
    fun testSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        coreSecurityDsl.apply(http)
        http {
            authorizeHttpRequests {
                authorize(anyRequest, permitAll)
            }
        }
        return http.build()
    }
}
