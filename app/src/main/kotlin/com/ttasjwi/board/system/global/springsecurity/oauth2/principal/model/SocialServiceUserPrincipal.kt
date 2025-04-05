package com.ttasjwi.board.system.global.springsecurity.oauth2.principal.model

interface SocialServiceUserPrincipal {
    fun socialServiceName(): String
    fun socialServiceUserId(): String
    fun email(): String
}
