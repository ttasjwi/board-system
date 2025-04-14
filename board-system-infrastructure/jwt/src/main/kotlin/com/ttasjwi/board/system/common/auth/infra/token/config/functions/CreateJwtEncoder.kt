package com.ttasjwi.board.system.common.auth.infra.token.config.functions

import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.core.io.Resource
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder


fun createJwtEncoder(
    rsaKeyId: String,
    publicKeyResource: Resource,
    privateKeyResource: Resource,
): JwtEncoder {
    return NimbusJwtEncoder(getEncoderJwkSource(rsaKeyId, publicKeyResource, privateKeyResource))
}

private fun getEncoderJwkSource(
    rsaKeyId: String,
    publicKeyResource: Resource,
    privateKeyResource: Resource,
): JWKSource<SecurityContext> {
    val rsaKey = loadEncoderRSAKey(
        rsaKeyId = rsaKeyId,
        publicKeyResource = publicKeyResource,
        privateKeyResource = privateKeyResource,
    )
    val jwkSet = JWKSet(rsaKey)
    return ImmutableJWKSet(jwkSet)
}
