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
                // 컨트롤러 진입에 대한 인증은, 컨트롤러 메서드에 부착된 어노테이션 + AOP 활용
                authorize(anyRequest, permitAll)
            }
        }
        return http.build()
    }
}
