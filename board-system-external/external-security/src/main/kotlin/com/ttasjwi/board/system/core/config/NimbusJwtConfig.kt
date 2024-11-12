package com.ttasjwi.board.system.core.config

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.JWSKeySelector
import com.nimbusds.jose.proc.JWSVerificationKeySelector
import com.nimbusds.jose.proc.SecurityContext
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor
import com.nimbusds.jwt.proc.DefaultJWTProcessor
import com.nimbusds.jwt.proc.JWTClaimsSetVerifier
import com.ttasjwi.board.system.auth.domain.external.spring.security.X509CertificateThumbprintValidator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ResourceLoader
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import java.nio.charset.StandardCharsets
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*

@Configuration
class NimbusJwtConfig(
    private val rsaKeyPairProperties: RsaKeyPairProperties,
    private val resourceLoader: ResourceLoader
) {

    @Bean
    fun jwtEncoder(jwkSource: JWKSource<SecurityContext>): NimbusJwtEncoder {
        return NimbusJwtEncoder(jwkSource)
    }

    @Bean
    fun jwtDecoder(jwkSource: JWKSource<SecurityContext>): NimbusJwtDecoder {
        val jwsAlgs = mutableSetOf<JWSAlgorithm>()
        jwsAlgs.addAll(JWSAlgorithm.Family.RSA)
        jwsAlgs.addAll(JWSAlgorithm.Family.EC)
        jwsAlgs.addAll(JWSAlgorithm.Family.HMAC_SHA)

        val jwtProcessor: ConfigurableJWTProcessor<SecurityContext> = DefaultJWTProcessor()
        val jwsKeySelector: JWSKeySelector<SecurityContext> = JWSVerificationKeySelector(jwsAlgs, jwkSource)
        jwtProcessor.jwsKeySelector = jwsKeySelector

        jwtProcessor.jwtClaimsSetVerifier = JWTClaimsSetVerifier { _: JWTClaimsSet?, _: SecurityContext? -> }
        val encoder = NimbusJwtDecoder(jwtProcessor)

        // 인코더에서 시간 검증하는 Validator 가 기본 구현체로 등록되어 있는데 이 부분은 우리 코드에서 해야함
        // 이 부분을 커스터마이징 함 -> X509CertificateThumbprintValidator 만 사용하도록함
        // 근데 이 부분은 해당 모듈 디폴트 클래스로 등록되어 있어서, 그대로 복붙해다가 새로 만들어서 사용함
        encoder.setJwtValidator(X509CertificateThumbprintValidator(X509CertificateThumbprintValidator.DEFAULT_X509_CERTIFICATE_SUPPLIER))
        return encoder
    }

    @Bean
    fun jwkSource(): JWKSource<SecurityContext> {
        val rsaKey = getRsaKey()
        val jwkSet = JWKSet(rsaKey)
        return ImmutableJWKSet(jwkSet)
    }

    private fun getRsaKey(): RSAKey {
        return RSAKey
            .Builder(loadRSAPublicKey())
            .privateKey(loadRSAPrivateKey())
            .keyID("keyPairId")
            .build()
    }

    /**
     * 파일로부터 RSA 공개키를 가져온뒤 RSAPublicKey 객체 생성
     */
    private fun loadRSAPublicKey(): RSAPublicKey {
        val resource = resourceLoader.getResource(rsaKeyPairProperties.publicKeyPath)
        val key = String(resource.inputStream.readAllBytes(), StandardCharsets.UTF_8)
            .replace("-----BEGIN PUBLIC KEY-----", "")
            .replace("-----END PUBLIC KEY-----", "")
            .replace("\\s".toRegex(), "")

        val decoded = Base64.getDecoder().decode(key)
        val spec = X509EncodedKeySpec(decoded)
        val keyFactory = KeyFactory.getInstance("RSA")
        return keyFactory.generatePublic(spec) as RSAPublicKey
    }

    /**
     * 파일로부터 RSA 개인키를 가져온뒤 RSAPrivateKey 객체 생성
     */
    private fun loadRSAPrivateKey(): RSAPrivateKey {
        val resource = resourceLoader.getResource(rsaKeyPairProperties.privateKeyPath)

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
