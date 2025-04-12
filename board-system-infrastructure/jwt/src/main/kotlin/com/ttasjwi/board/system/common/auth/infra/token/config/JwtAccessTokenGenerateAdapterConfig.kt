package com.ttasjwi.board.system.common.auth.infra.token.config

import com.ttasjwi.board.system.common.auth.AccessTokenGeneratePort
import com.ttasjwi.board.system.common.auth.infra.token.JwtAccessTokenGenerateAdapter
import com.ttasjwi.board.system.common.auth.infra.token.config.functions.createJwtEncoder
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ResourceLoader

@Configuration
@ConditionalOnProperty(name = ["accessToken.rsaKeyPath.public", "accessToken.rsaKeyPath.private"])
class JwtAccessTokenGenerateAdapterConfig(
    private val resourceLoader: ResourceLoader,

    @Value("\${accessToken.rsaKeyPath.public}")
    private val publicKeyPath: String,

    @Value("\${accessToken.rsaKeyPath.private}")
    private val privateKeyPath: String,
) {

    @Bean
    fun accessTokenGeneratePort(): AccessTokenGeneratePort {
        val jwtEncoder = createJwtEncoder(
            rsaKeyId = "AccessTokenRSAKey",
            publicKeyResource = resourceLoader.getResource(publicKeyPath),
            privateKeyResource = resourceLoader.getResource(privateKeyPath)
        )
        return JwtAccessTokenGenerateAdapter(jwtEncoder)
    }
}
