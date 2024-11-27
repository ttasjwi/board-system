package org.springframework.security.oauth2.core.endpoint.fixture

import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest

fun oAuth2AuthorizationRequestFixture(
    authorizationUri: String = "authorizationUri",
    clientId: String = "clientId",
    redirectUri: String = "redirectUri",
    scopes: Set<String> = setOf("openid", "profile"),
    state: String = "state123456",
    additionalParameters: Map<String, Any> = mapOf("additional1" to "additionalValue1", "additional2" to "additionalValue2"),
    authorizationRequestUri: String = "authorizationUri",
    attributes: Map<String, Any> = mapOf("key1" to "value1", "key2" to "value2")
): OAuth2AuthorizationRequest {
    return OAuth2AuthorizationRequest.authorizationCode()
        .authorizationUri(authorizationUri)
        .clientId(clientId)
        .redirectUri(redirectUri)
        .scopes(scopes)
        .state(state)
        .additionalParameters(additionalParameters)
        .authorizationRequestUri(authorizationRequestUri)
        .attributes(attributes)
        .build()
}
