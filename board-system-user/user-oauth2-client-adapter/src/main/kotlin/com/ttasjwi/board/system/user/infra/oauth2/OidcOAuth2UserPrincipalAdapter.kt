package com.ttasjwi.board.system.user.infra.oauth2

import com.nimbusds.jose.JWSVerifier
import com.nimbusds.jose.crypto.RSASSAVerifier
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import com.ttasjwi.board.system.user.domain.model.OAuth2AuthorizationRequest
import com.ttasjwi.board.system.user.domain.model.OAuth2ClientRegistration
import com.ttasjwi.board.system.user.domain.model.OAuth2UserPrincipal
import com.ttasjwi.board.system.user.domain.port.OidcOAuth2UserPrincipalPort
import org.springframework.stereotype.Component
import java.net.URI

@Component
class OidcOAuth2UserPrincipalAdapter : OidcOAuth2UserPrincipalPort {


    override fun getUserPrincipal(
        idToken: String,
        oAuth2ClientRegistration: OAuth2ClientRegistration,
        oAuth2AuthorizationRequest: OAuth2AuthorizationRequest
    ): OAuth2UserPrincipal {
        val jwt = SignedJWT.parse(idToken)
        validateJwt(jwt, oAuth2ClientRegistration, oAuth2AuthorizationRequest)
        return mapToOAuth2UserPrincipal(jwt.jwtClaimsSet, oAuth2ClientRegistration)
    }

    private fun validateJwt(
        jwt: SignedJWT,
        oAuth2ClientRegistration: OAuth2ClientRegistration,
        oAuth2AuthorizationRequest: OAuth2AuthorizationRequest
    ) {
        val issuerUri = oAuth2ClientRegistration.providerDetails.issuerUri
        val clientId = oAuth2ClientRegistration.clientId
        val claims = jwt.jwtClaimsSet

        verifySignature(oAuth2ClientRegistration, jwt)
        verifyIssuer(claims, issuerUri)
        verifyAudience(claims, clientId)
        verifyNonce(claims, oAuth2AuthorizationRequest)
    }

    private fun verifySignature(
        oAuth2ClientRegistration: OAuth2ClientRegistration,
        jwt: SignedJWT
    ) {
        val jwkSet = JWKSet.load(URI.create(oAuth2ClientRegistration.providerDetails.jwkSetUri!!).toURL())
        val kid = jwt.header.keyID ?: error("No 'kid' found in JWT header")
        val jwk = jwkSet.getKeyByKeyId(kid) as? RSAKey
            ?: error("No matching JWK found for kid: $kid")

        val verifier: JWSVerifier = RSASSAVerifier(jwk)
        if (!jwt.verify(verifier)) {
            throw IllegalArgumentException("Invalid JWT signature")
        }
    }

    private fun verifyIssuer(claims: JWTClaimsSet, issuerUri: String?) {
        if (claims.issuer != issuerUri) {
            throw IllegalStateException("Issuer: $issuerUri is not a valid issuer")
        }
    }

    private fun verifyAudience(claims: JWTClaimsSet, clientId: String) {
        if (!claims.audience.contains(clientId)) {
            throw IllegalStateException("audience: clientId - $clientId is not a valid audience")
        }
    }

    private fun verifyNonce(
        claims: JWTClaimsSet,
        oAuth2AuthorizationRequest: OAuth2AuthorizationRequest
    ) {
        val tokenNonce = claims.getClaim("nonce") as String
        if (!oAuth2AuthorizationRequest.matchesNonce(tokenNonce)) {
            throw IllegalStateException("Invalid nonce - tokenNonce: $tokenNonce")
        }
    }

    private fun mapToOAuth2UserPrincipal(claims: JWTClaimsSet, oAuth2ClientRegistration: OAuth2ClientRegistration): OAuth2UserPrincipal {
        return when (oAuth2ClientRegistration.registrationId) {
            "google" -> {
                OAuth2UserPrincipal(
                    socialServiceName = oAuth2ClientRegistration.registrationId,
                    socialServiceUserId = claims.getStringClaim("sub"),
                    email = claims.getStringClaim("email"),
                )
            }
            "kakao" -> {
                OAuth2UserPrincipal(
                    socialServiceName = oAuth2ClientRegistration.registrationId,
                    socialServiceUserId = claims.getStringClaim("sub"),
                    email = claims.getStringClaim("email"),
                )
            }
            else ->  throw IllegalStateException("Oidc 방식 사용자 정보 획득은 google, kakao 만 지원됨")
        }
    }
}
