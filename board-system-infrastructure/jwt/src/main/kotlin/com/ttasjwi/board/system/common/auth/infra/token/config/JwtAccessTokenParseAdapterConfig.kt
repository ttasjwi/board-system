package com.ttasjwi.board.system.common.auth.infra.token.config

import com.ttasjwi.board.system.common.auth.AccessTokenParsePort
import com.ttasjwi.board.system.common.auth.infra.token.JwtAccessTokenParseAdapter
import com.ttasjwi.board.system.common.auth.infra.token.config.functions.createJwtDecoder
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ResourceLoader

@Configuration
@ConditionalOnProperty(name = ["accessToken.rsaKeyPath.public"])
class JwtAccessTokenParseAdapterConfig(
    private val resourceLoader: ResourceLoader,
    @Value("\${accessToken.rsaKeyPath.public}")
    private val publicKeyPath: String,
) {

    @Bean
    fun accessTokenParsePort(): AccessTokenParsePort {
        val jwtDecoder = createJwtDecoder(
            rsaKeyId = "AccessTokenRSAKey",
            publicKeyResource = resourceLoader.getResource(publicKeyPath)
        )
        return JwtAccessTokenParseAdapter(jwtDecoder)
    }
}
