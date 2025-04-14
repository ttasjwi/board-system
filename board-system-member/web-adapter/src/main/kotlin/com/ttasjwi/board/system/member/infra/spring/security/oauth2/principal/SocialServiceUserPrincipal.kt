package com.ttasjwi.board.system.member.infra.spring.security.oauth2.principal

interface SocialServiceUserPrincipal {
    fun socialServiceName(): String
    fun socialServiceUserId(): String
    fun email(): String
}
