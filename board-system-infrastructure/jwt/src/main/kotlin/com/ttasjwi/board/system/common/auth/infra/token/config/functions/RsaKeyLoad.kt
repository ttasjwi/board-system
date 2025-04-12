package com.ttasjwi.board.system.common.auth.infra.token.config.functions

import com.nimbusds.jose.jwk.RSAKey
import org.springframework.core.io.Resource
import java.nio.charset.StandardCharsets
import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*

fun loadEncoderRSAKey(rsaKeyId: String, publicKeyResource: Resource, privateKeyResource: Resource): RSAKey {
    return RSAKey
        .Builder(loadRSAPublicKey(publicKeyResource))
        .privateKey(loadRSAPrivateKey(privateKeyResource))
        .keyID(rsaKeyId)
        .build()
}

fun loadDecoderRSAKey(rsaKeyId: String, publicKeyResource: Resource): RSAKey {
    return RSAKey
        .Builder(loadRSAPublicKey(publicKeyResource))
        .keyID(rsaKeyId)
        .build()
}

private fun loadRSAPublicKey(publicKeyResource: Resource): RSAPublicKey {
    val key = readAllBytes(publicKeyResource)
        .replace("-----BEGIN PUBLIC KEY-----", "")
        .replace("-----END PUBLIC KEY-----", "")
        .replace("\\s".toRegex(), "")
    val decoded = Base64.getDecoder().decode(key)
    val spec = X509EncodedKeySpec(decoded)
    val factory = KeyFactory.getInstance("RSA")
    return factory.generatePublic(spec) as RSAPublicKey
}

private fun loadRSAPrivateKey(privateKeyResource: Resource): RSAPrivateKey {
    val key = readAllBytes(privateKeyResource)
        .replace("-----BEGIN PRIVATE KEY-----", "")
        .replace("-----END PRIVATE KEY-----", "")
        .replace("\\s".toRegex(), "")
    val decoded = Base64.getDecoder().decode(key)
    val spec = PKCS8EncodedKeySpec(decoded)
    val keyFactory = KeyFactory.getInstance("RSA")
    return keyFactory.generatePrivate(spec) as RSAPrivateKey
}

private fun readAllBytes(resource: Resource): String {
    return String(resource.inputStream.readAllBytes(), StandardCharsets.UTF_8)
}
