package com.ttasjwi.board.system.common.websupport.auth.config

import com.ttasjwi.board.system.common.websupport.auth.token.JwtAccessTokenParser
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ResourceLoader
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import java.nio.charset.StandardCharsets
import java.security.KeyFactory
import java.security.interfaces.RSAPublicKey
import java.security.spec.X509EncodedKeySpec
import java.util.Base64.getDecoder

class AccessTokenParserConfig(
    private val resourceLoader: ResourceLoader,
    @Value("\${rsaKeyPath.public}")
    private val publicKeyPath: String,
) {

    @Bean
    fun jwtAccessTokenParser(): JwtAccessTokenParser {
        return JwtAccessTokenParser(jwtDecoder())
    }

    @Bean
    fun jwtDecoder(): NimbusJwtDecoder {
        val publicKey = loadRSAPublicKey()
        return NimbusJwtDecoder.withPublicKey(publicKey).build()
    }

    private fun loadRSAPublicKey(): RSAPublicKey {
        val resource = resourceLoader.getResource(publicKeyPath)
        val key = String(resource.inputStream.readAllBytes(), StandardCharsets.UTF_8)
            .replace("-----BEGIN PUBLIC KEY-----", "")
            .replace("-----END PUBLIC KEY-----", "")
            .replace("\\s".toRegex(), "")
        val decoded = getDecoder().decode(key)
        val spec = X509EncodedKeySpec(decoded)
        val factory = KeyFactory.getInstance("RSA")
        return factory.generatePublic(spec) as RSAPublicKey
    }
}
