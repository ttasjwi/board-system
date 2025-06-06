package com.ttasjwi.board.system.common.infra.websupport.auth.config

import com.ttasjwi.board.system.common.auth.AccessTokenParsePort
import com.ttasjwi.board.system.common.auth.AuthUserLoader
import com.ttasjwi.board.system.common.infra.websupport.auth.security.MethodAuthorizationAspect
import com.ttasjwi.board.system.common.infra.websupport.auth.security.SecurityAuthUserLoader
import com.ttasjwi.board.system.common.time.TimeManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.web.servlet.HandlerExceptionResolver

@EnableWebSecurity
@Configuration
class CoreSecurityConfig(
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
    fun authUserLoader(): AuthUserLoader {
        return SecurityAuthUserLoader()
    }

    @Bean
    fun methodSecurityAspect(): MethodAuthorizationAspect {
        return MethodAuthorizationAspect(authUserLoader())
    }
}
