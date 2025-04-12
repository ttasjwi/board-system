package com.ttasjwi.board.system.app.config

import com.ttasjwi.board.system.common.websupport.auth.config.CoreSecurityDsl
import com.ttasjwi.board.system.common.websupport.auth.config.SecurityConfig
import com.ttasjwi.board.system.common.websupport.exception.config.ExceptionHandlingConfig
import com.ttasjwi.board.system.common.websupport.locale.config.LocaleConfig
import com.ttasjwi.board.system.common.websupport.message.config.MessageConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.SecurityFilterChain

@Configuration
@Import(
    MessageConfig::class,
    LocaleConfig::class,
    SecurityConfig::class,
    ExceptionHandlingConfig::class,
)
class AppWebSupportConfig(
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
