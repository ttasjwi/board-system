package com.ttasjwi.board.system.user.domain.model

import com.ttasjwi.board.system.user.domain.util.Base64SecureKeyGenerator
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util.*

class OAuth2AuthorizationRequest
internal constructor(
    val authorizationUri: String,
    val oAuth2ClientRegistrationId: String,
    val responseType: OAuth2AuthorizationResponseType,
    val clientId: String,
    val redirectUri: String,
    val scopes: Set<String>,
    val state: String,
    val pkceParams: PKCEParams,
    val nonceParams: NonceParams?,
) {

    val authorizationRequestUri: String by lazy {
        val queryParams = mutableListOf(
            "client_id" to clientId,
            "redirect_uri" to redirectUri,
            "response_type" to responseType.value,
            "scope" to scopes.joinToString(" "),
            "state" to state,
            "code_challenge" to pkceParams.codeChallenge,
            "code_challenge_method" to pkceParams.codeChallengeMethod,
        )

        if (this.nonceParams != null) {
            queryParams.add("nonce" to nonceParams.nonce)
        }
        buildUriWithQuery(authorizationUri, queryParams)
    }

    companion object {

        private val stateGenerator: Base64SecureKeyGenerator = Base64SecureKeyGenerator.init(Base64.getUrlEncoder())
        private val secureKeyGenerator: Base64SecureKeyGenerator =
            Base64SecureKeyGenerator.init(Base64.getUrlEncoder().withoutPadding(), 96)

        private const val MESSAGE_DIGEST_ALGORITHM = "SHA-256"

        fun create(clientRegistration: OAuth2ClientRegistration): OAuth2AuthorizationRequest {
            return OAuth2AuthorizationRequest(
                authorizationUri = clientRegistration.providerDetails.authorizationUri,
                oAuth2ClientRegistrationId = clientRegistration.registrationId,
                responseType = OAuth2AuthorizationResponseType.CODE,
                clientId = clientRegistration.clientId,
                redirectUri = clientRegistration.redirectUri,
                scopes = clientRegistration.scopes,
                state = stateGenerator.generateKey(),
                pkceParams = PKCEParams.create(),
                nonceParams = generateNonceParamsIfNecessary(clientRegistration),
            )
        }

        fun restore(
            authorizationUri: String,
            oAuth2ClientRegistrationId: String,
            responseType: String,
            clientId: String,
            redirectUri: String,
            scopes: Set<String>,
            state: String,
            codeChallenge: String,
            codeChallengeMethod: String,
            codeVerifier: String,
            nonce: String?,
            nonceHash: String?,
        ): OAuth2AuthorizationRequest {
            return OAuth2AuthorizationRequest(
                authorizationUri = authorizationUri,
                oAuth2ClientRegistrationId = oAuth2ClientRegistrationId,
                responseType = OAuth2AuthorizationResponseType.restore(responseType),
                clientId = clientId,
                redirectUri = redirectUri,
                scopes = scopes,
                state = state,
                pkceParams = PKCEParams.restore(codeChallenge, codeChallengeMethod, codeVerifier),
                nonceParams = nonce?.let { NonceParams.restore(it, nonceHash!!) }
            )
        }

        /**
         * Scope 목록에 "openid"가 있으면 Nonce 적용
         */
        private fun generateNonceParamsIfNecessary(clientRegistration: OAuth2ClientRegistration): NonceParams? {
            // Scope 목록에 "openid"가 있으면 Nonce 적용, 없으면 null 반환
            if (!clientRegistration.scopes.contains("openid")) {
                return null
            }
            return NonceParams.create()
        }

        private fun createHash(value: String): String {
            val md = MessageDigest.getInstance(MESSAGE_DIGEST_ALGORITHM)
            val digest = md.digest(value.toByteArray(StandardCharsets.US_ASCII))
            return Base64.getUrlEncoder().withoutPadding().encodeToString(digest)
        }
    }

    fun matchesNonce(idTokenNonce: String): Boolean {
        val idTokenNonceHash = createHash(idTokenNonce)
        return idTokenNonceHash == nonceParams!!.nonceHash
    }

    private fun buildUriWithQuery(base: String, queryParams: List<Pair<String, String>>): String {
        val queryString = queryParams.joinToString("&") { (k, v) ->
            "${encodeQueryParam(k)}=${encodeQueryParam(v)}"
        }
        return "$base?$queryString"
    }

    private fun encodeQueryParam(value: String): String {
        return URLEncoder.encode(value, StandardCharsets.UTF_8.toString())
            .replace("+", "%20")
    }

    data class NonceParams(
        val nonce: String,
        val nonceHash: String,
    ) {

        companion object {

            fun create(): NonceParams {
                // 원본 값
                val nonce = secureKeyGenerator.generateKey()

                // 해시된 값
                val nonceHash = createHash(nonce)

                return NonceParams(
                    nonce = nonce,
                    nonceHash = nonceHash
                )
            }

            fun restore(nonce: String, nonceHash: String): NonceParams {
                return NonceParams(nonce, nonceHash)
            }
        }
    }

    data class PKCEParams(
        val codeChallenge: String,
        val codeChallengeMethod: String,
        val codeVerifier: String
    ) {

        companion object {

            internal const val DEFAULT_CODE_CHALLENGE_METHOD = "S256"

            fun create(): PKCEParams {
                // 원본 값
                val codeVerifier = secureKeyGenerator.generateKey()

                // 알고리즘
                val codeChallengeMethod = DEFAULT_CODE_CHALLENGE_METHOD


                // 해시된 값
                val codeChallenge = createHash(codeVerifier)

                return PKCEParams(
                    codeVerifier = codeVerifier,
                    codeChallengeMethod = codeChallengeMethod,
                    codeChallenge = codeChallenge
                )
            }

            fun restore(
                codeChallenge: String,
                codeChallengeMethod: String,
                codeVerifier: String,
            ): PKCEParams {
                return PKCEParams(
                    codeChallenge = codeChallenge,
                    codeChallengeMethod = codeChallengeMethod,
                    codeVerifier = codeVerifier
                )
            }
        }
    }
}
