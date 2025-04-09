package com.ttasjwi.board.system.common.websupport.auth.config

import com.nimbusds.jose.jwk.JWKSet
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ResourceLoader
import org.springframework.security.oauth2.jwt.JwtEncoder
import java.nio.charset.StandardCharsets
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.spec.PKCS8EncodedKeySpec
import java.util.*
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import com.ttasjwi.board.system.common.token.AccessTokenGenerator
import com.ttasjwi.board.system.common.websupport.auth.token.JwtAccessTokenGenerator
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import java.security.interfaces.RSAPublicKey
import java.security.spec.X509EncodedKeySpec

@Configuration
@ConditionalOnProperty(name = ["rsaKeyPath.private"])
class AccessTokenGeneratorConfig(
    private val resourceLoader: ResourceLoader,
    @Value("\${rsaKeyPath.public}")
    private val publicKeyPath: String,

    @Value("\${rsaKeyPath.private}")
    private val privateKeyPath: String,
) {

    @Bean
    fun accessTokenGenerator(): AccessTokenGenerator {
        return JwtAccessTokenGenerator(jwtEncoder())
    }

    @Bean
    fun jwtEncoder(): JwtEncoder {
        val jwkSource = getEncoderJwkSource()
        return NimbusJwtEncoder(jwkSource)
    }

    private fun getEncoderJwkSource(): JWKSource<SecurityContext> {
        val rsaKey = getEncoderRsaKey()
        val jwkSet = JWKSet(rsaKey)
        return ImmutableJWKSet(jwkSet)
    }

    private fun getEncoderRsaKey(): RSAKey {
        return RSAKey
            .Builder(loadRSAPublicKey())
            .privateKey(loadRSAPrivateKey())
            .keyID("keyPairId")
            .build()
    }

    private fun loadRSAPublicKey(): RSAPublicKey {
        val resource = resourceLoader.getResource(publicKeyPath)
        val key = String(resource.inputStream.readAllBytes(), StandardCharsets.UTF_8)
            .replace("-----BEGIN PUBLIC KEY-----", "")
            .replace("-----END PUBLIC KEY-----", "")
            .replace("\\s".toRegex(), "")
        val decoded = Base64.getDecoder().decode(key)
        val spec = X509EncodedKeySpec(decoded)
        val factory = KeyFactory.getInstance("RSA")
        return factory.generatePublic(spec) as RSAPublicKey
    }

    private fun loadRSAPrivateKey(): RSAPrivateKey {
        val resource = resourceLoader.getResource(privateKeyPath)
        val key = String(resource.inputStream.readAllBytes(), StandardCharsets.UTF_8)
            .replace("-----BEGIN PRIVATE KEY-----", "")
            .replace("-----END PRIVATE KEY-----", "")
            .replace("\\s".toRegex(), "")
        val decoded = Base64.getDecoder().decode(key)
        val spec = PKCS8EncodedKeySpec(decoded)
        val keyFactory = KeyFactory.getInstance("RSA")
        return keyFactory.generatePrivate(spec) as RSAPrivateKey
    }
}
