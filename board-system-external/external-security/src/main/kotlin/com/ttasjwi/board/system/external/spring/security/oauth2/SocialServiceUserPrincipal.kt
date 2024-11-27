package com.ttasjwi.board.system.external.spring.security.oauth2

interface SocialServiceUserPrincipal {
    fun socialServiceName(): String
    fun socialServiceUserId(): String
    fun email(): String
}
