package com.ttasjwi.board.system.user.infra.spring.security.oauth2.principal

interface SocialServiceUserPrincipal {
    fun socialServiceName(): String
    fun socialServiceUserId(): String
    fun email(): String
}
