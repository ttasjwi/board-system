package com.ttasjwi.board.system.common.auth.infra.token.config.functions

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.JWSKeySelector
import com.nimbusds.jose.proc.JWSVerificationKeySelector
import com.nimbusds.jose.proc.SecurityContext
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor
import com.nimbusds.jwt.proc.DefaultJWTProcessor
import com.nimbusds.jwt.proc.JWTClaimsSetVerifier
import com.ttasjwi.board.system.common.auth.infra.token.X509CertificateThumbprintValidator
import org.springframework.core.io.Resource
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder

fun createJwtDecoder(rsaKeyId: String, publicKeyResource: Resource): NimbusJwtDecoder {
    val jwkSource = decoderJwkSource(
        rsaKeyId = rsaKeyId,
        publicKeyResource = publicKeyResource,
    )

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

private fun decoderJwkSource(rsaKeyId: String, publicKeyResource: Resource): JWKSource<SecurityContext> {
    val rsaKey = loadDecoderRSAKey(
        rsaKeyId = rsaKeyId,
        publicKeyResource = publicKeyResource
    )
    val jwkSet = JWKSet(rsaKey)
    return ImmutableJWKSet(jwkSet)
}
