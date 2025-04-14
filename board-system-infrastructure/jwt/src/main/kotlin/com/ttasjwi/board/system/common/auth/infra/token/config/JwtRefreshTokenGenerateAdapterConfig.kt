package com.ttasjwi.board.system.common.auth.infra.token.config

import com.ttasjwi.board.system.common.auth.RefreshTokenGeneratePort
import com.ttasjwi.board.system.common.auth.infra.token.JwtRefreshTokenGenerateAdapter
import com.ttasjwi.board.system.common.auth.infra.token.config.functions.createJwtEncoder
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ResourceLoader

@Configuration
@ConditionalOnProperty(name = ["refreshToken.rsaKeyPath.public", "refreshToken.rsaKeyPath.private"])
class JwtRefreshTokenGenerateAdapterConfig(
    private val resourceLoader: ResourceLoader,

    @Value("\${refreshToken.rsaKeyPath.public}")
    private val publicKeyPath: String,

    @Value("\${refreshToken.rsaKeyPath.private}")
    private val privateKeyPath: String,
) {

    @Bean
    fun refreshTokenGeneratePort(): RefreshTokenGeneratePort {
        val jwtEncoder = createJwtEncoder(
            rsaKeyId = "RefreshTokenRSAKey",
            publicKeyResource = resourceLoader.getResource(publicKeyPath),
            privateKeyResource = resourceLoader.getResource(privateKeyPath)
        )
        return JwtRefreshTokenGenerateAdapter(jwtEncoder)
    }
}
