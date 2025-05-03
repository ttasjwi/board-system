package com.ttasjwi.board.system.user.domain

interface SocialServiceAuthorizationUseCase {
    fun generateAuthorizationRequestUri(socialServiceId: String): String
}
