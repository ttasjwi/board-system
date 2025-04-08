package com.ttasjwi.board.system.common.websupport.auth.config

import com.ttasjwi.board.system.common.auth.AuthMemberLoader
import com.ttasjwi.board.system.common.time.TimeManager
import com.ttasjwi.board.system.common.token.AccessTokenParser
import com.ttasjwi.board.system.common.websupport.auth.security.MethodAuthorizationAspect
import com.ttasjwi.board.system.common.websupport.auth.security.SecurityAuthMemberLoader
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.web.servlet.HandlerExceptionResolver

@EnableWebSecurity
@Configuration
@Import(AccessTokenParserConfig::class)
class SecurityConfig(
    private val accessTokenParser: AccessTokenParser,
    private val handlerExceptionResolver: HandlerExceptionResolver,
    private val timeManager: TimeManager
) {

    @Bean
    fun coreSecurityDsl(): CoreSecurityDsl {
        return CoreSecurityDsl(
            accessTokenParser = accessTokenParser,
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
