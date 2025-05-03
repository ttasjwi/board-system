package com.ttasjwi.board.system.user.domain.service

import com.ttasjwi.board.system.common.annotation.component.DomainService
import com.ttasjwi.board.system.user.domain.model.OAuth2AuthorizationRequest
import com.ttasjwi.board.system.user.domain.model.OAuth2AuthorizationResponseType
import com.ttasjwi.board.system.user.domain.model.OAuth2ClientRegistration
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util.*

@DomainService
class OAuth2AuthorizationRequestGenerator {

    private val stateGenerator: Base64SecureKeyGenerator = Base64SecureKeyGenerator.init(Base64.getUrlEncoder())
    private val secureKeyGenerator: Base64SecureKeyGenerator =
        Base64SecureKeyGenerator.init(Base64.getUrlEncoder().withoutPadding(), 96)

    /**
     * OAuth2 인가요청을 생성합니다.
     */
    fun generate(clientRegistration: OAuth2ClientRegistration): OAuth2AuthorizationRequest {
        return OAuth2AuthorizationRequest.create(
            authorizationUri = clientRegistration.providerDetails.authorizationUri,
            oAuth2ClientRegistrationId = clientRegistration.registrationId,
            responseType = OAuth2AuthorizationResponseType.CODE,
            clientId = clientRegistration.clientId,
            redirectUri = clientRegistration.redirectUri,
            scopes = clientRegistration.scopes,
            state = stateGenerator.generateKey(),
            pkceParams = generatePKCEParams(),
            nonceParams = generateNonceParamsIfNecessary(clientRegistration),
        )
    }

    /**
     * PKCE 적용
     */
    private fun generatePKCEParams(): OAuth2AuthorizationRequest.PKCEParams {
        // 원본 값
        val codeVerifier = secureKeyGenerator.generateKey()

        // 알고리즘
        val codeChallengeMethod = "S256"


        // 해시된 값
        val codeChallenge = createHash(codeVerifier)

        return OAuth2AuthorizationRequest.PKCEParams(
            codeVerifier = codeVerifier,
            codeChallengeMethod = codeChallengeMethod,
            codeChallenge = codeChallenge
        )
    }

    /**
     * Scope 목록에 "openid"가 있으면 Nonce 적용
     */
    private fun generateNonceParamsIfNecessary(clientRegistration: OAuth2ClientRegistration): OAuth2AuthorizationRequest.NonceParams? {
        // Scope 목록에 "openid"가 있으면 Nonce 적용, 없으면 null 반환
        if (!clientRegistration.scopes.contains("openid")) {
            return null
        }

        // 원본 값
        val nonce = secureKeyGenerator.generateKey()

        // 해시된 값
        val nonceHash = createHash(nonce)

        return OAuth2AuthorizationRequest.NonceParams(
            nonce = nonce,
            nonceHash = nonceHash
        )
    }

    private fun createHash(value: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(value.toByteArray(StandardCharsets.US_ASCII))
        return Base64.getUrlEncoder().withoutPadding().encodeToString(digest)
    }
}
