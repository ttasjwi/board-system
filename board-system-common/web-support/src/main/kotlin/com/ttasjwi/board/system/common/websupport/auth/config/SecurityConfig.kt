package com.ttasjwi.board.system.common.websupport.auth.config

import com.ttasjwi.board.system.common.auth.AccessTokenParsePort
import com.ttasjwi.board.system.common.auth.AuthMemberLoader
import com.ttasjwi.board.system.common.time.TimeManager
import com.ttasjwi.board.system.common.websupport.auth.security.MethodAuthorizationAspect
import com.ttasjwi.board.system.common.websupport.auth.security.SecurityAuthMemberLoader
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.web.servlet.HandlerExceptionResolver

@EnableWebSecurity
@Configuration
class SecurityConfig(
    private val accessTokenParsePort: AccessTokenParsePort,
    private val handlerExceptionResolver: HandlerExceptionResolver,
    private val timeManager: TimeManager
) {

    @Bean
    fun coreSecurityDsl(): CoreSecurityDsl {
        return CoreSecurityDsl(
            accessTokenParsePort = accessTokenParsePort,
            handlerExceptionResolver = handlerExceptionResolver,
            timeManager = timeManager
        )
    }

    @Bean
    fun authMemberLoader(): AuthMemberLoader {
        return SecurityAuthMemberLoader()
    }

    @Bean
    fun methodSecurityAspect(): MethodAuthorizationAspect {
        return MethodAuthorizationAspect(authMemberLoader())
    }
}
