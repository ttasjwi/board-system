package com.ttasjwi.board.system.user.domain.model.fixture

import com.ttasjwi.board.system.user.domain.model.OAuth2ClientAuthenticationMethod
import com.ttasjwi.board.system.user.domain.model.OAuth2ClientRegistration

fun naverOAuth2ClientRegistrationFixture(): OAuth2ClientRegistration {
    return OAuth2ClientRegistration(
        registrationId = "naver",
        clientId = "naver-client-id",
        clientSecret = "naver-client-secret",
        clientAuthenticationMethod = OAuth2ClientAuthenticationMethod.CLIENT_SECRET_BASIC,
        redirectUri = "http://redirect-uri/naver",
        scopes = setOf("email"),
        clientName = "naver",
        providerDetails = OAuth2ClientRegistration.ProviderDetails(
            authorizationUri = "https://naver-authorization-uri",
            tokenUri = "https://naver-token-uri",
            userInfoEndpoint = OAuth2ClientRegistration.ProviderDetails.UserInfoEndpoint(
                uri = "https://naver-user-info-uri",
                authenticationMethod = OAuth2ClientAuthenticationMethod.HEADER,
                usernameAttributeName = "response"
            ),
            jwkSetUri = null,
            issuerUri = null,
            configurationMetadata = mapOf(),
        )
    )
}

fun kakaoOAuth2ClientRegistrationFixgture(): OAuth2ClientRegistration {
    return OAuth2ClientRegistration(
        registrationId = "kakao",
        clientId = "kakao-client-id",
        clientSecret = "kakao-client-secret",
        clientAuthenticationMethod = OAuth2ClientAuthenticationMethod.CLIENT_SECRET_POST,
        redirectUri = "http://redirect-uri/kakao",
        scopes = setOf("openid", "account_email"),
        clientName = "https://kauth.kakao.com",
        providerDetails = OAuth2ClientRegistration.ProviderDetails(
            authorizationUri = "https://kakao-authorization-uri",
            tokenUri = "https://kakao-token-uri",
            userInfoEndpoint = OAuth2ClientRegistration.ProviderDetails.UserInfoEndpoint(
                uri = "https://kakao-user-info-uri",
                authenticationMethod = OAuth2ClientAuthenticationMethod.HEADER,
                usernameAttributeName = "sub"
            ),
            jwkSetUri = "https://kauth.kakao.com/.ell-known/jwks.json",
            issuerUri = "https://kauth.kakao.com",
            configurationMetadata = mapOf(),
        )
    )
}

fun googleOAuth2ClientRegistrationFixture(): OAuth2ClientRegistration {
    return OAuth2ClientRegistration(
        registrationId = "google",
        clientId = "google-client-id",
        clientSecret = "google-client-secret",
        clientAuthenticationMethod = OAuth2ClientAuthenticationMethod.CLIENT_SECRET_BASIC,
        redirectUri = "http://redirect-uri/google",
        scopes = setOf("openid", "email"),
        clientName = "Google",
        providerDetails = OAuth2ClientRegistration.ProviderDetails(
            authorizationUri = "https://google-authorization-uri",
            tokenUri = "https://google-token-uri",
            userInfoEndpoint = OAuth2ClientRegistration.ProviderDetails.UserInfoEndpoint(
                uri = "https://google-user-info-uri",
                authenticationMethod = OAuth2ClientAuthenticationMethod.HEADER,
                usernameAttributeName = "sub"
            ),
            jwkSetUri = "https://google-jwk-uri",
            issuerUri = "https://google-issuer-uri",
            configurationMetadata = mapOf(),
        )
    )
}
