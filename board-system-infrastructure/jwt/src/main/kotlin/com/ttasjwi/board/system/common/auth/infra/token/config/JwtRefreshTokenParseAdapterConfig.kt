package com.ttasjwi.board.system.common.auth.infra.token.config

import com.ttasjwi.board.system.common.auth.RefreshTokenParsePort
import com.ttasjwi.board.system.common.auth.infra.token.JwtRefreshTokenParseAdapter
import com.ttasjwi.board.system.common.auth.infra.token.config.functions.createJwtDecoder
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ResourceLoader

@Configuration
@ConditionalOnProperty(name = ["refreshToken.rsaKeyPath.public"])
class JwtRefreshTokenParseAdapterConfig(
    private val resourceLoader: ResourceLoader,
    @Value("\${refreshToken.rsaKeyPath.public}")
    private val publicKeyPath: String,
) {

    @Bean
    fun refreshTokenParsePort(): RefreshTokenParsePort {
        val jwtDecoder = createJwtDecoder(
            rsaKeyId = "RefreshTokenRSAKey",
            publicKeyResource = resourceLoader.getResource(publicKeyPath)
        )
        return JwtRefreshTokenParseAdapter(jwtDecoder)
    }
}
