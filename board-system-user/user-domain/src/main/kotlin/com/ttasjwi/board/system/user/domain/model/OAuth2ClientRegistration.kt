package com.ttasjwi.board.system.user.domain.model

class OAuth2ClientRegistration(
    val registrationId: String,
    val clientId: String,
    val clientSecret: String,
    val clientAuthenticationMethod: OAuth2ClientAuthenticationMethod,
    val redirectUri: String,
    val scopes: Set<String>,
    val clientName: String,
    val providerDetails: ProviderDetails
) {

    class ProviderDetails(
        val authorizationUri: String,
        val tokenUri: String,
        val userInfoEndpoint: UserInfoEndpoint,
        val jwkSetUri: String?,
        val issuerUri: String?,
        val configurationMetadata: Map<String, Any>
    ) {

        class UserInfoEndpoint(
            val uri: String,
            val authenticationMethod: OAuth2ClientAuthenticationMethod,
            val usernameAttributeName: String,
        )
    }
}
