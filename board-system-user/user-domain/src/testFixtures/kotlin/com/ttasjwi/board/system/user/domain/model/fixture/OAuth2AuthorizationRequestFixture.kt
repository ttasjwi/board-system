package com.ttasjwi.board.system.user.domain.model.fixture

import com.ttasjwi.board.system.user.domain.model.OAuth2AuthorizationRequest

fun naverOAuth2AuthorizationRequestFixture(): OAuth2AuthorizationRequest {
    return OAuth2AuthorizationRequest.create(naverOAuth2ClientRegistrationFixture())
}

fun kakaoOAuth2AuthorizationRequestFixture(): OAuth2AuthorizationRequest {
    return OAuth2AuthorizationRequest.create(kakaoOAuth2ClientRegistrationFixgture())
}

fun googleOAuth2AuthorizationRequestFixture(): OAuth2AuthorizationRequest {
    return OAuth2AuthorizationRequest.create(googleOAuth2ClientRegistrationFixture())
}
