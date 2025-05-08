package com.ttasjwi.board.system.user.domain.model

data class OAuth2UserPrincipal(
    val socialServiceName: String,
    val socialServiceUserId: String,
    val email: String
)
