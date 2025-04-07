package com.ttasjwi.board.system.auth.domain.external.impl

import com.ttasjwi.board.system.common.auth.AuthMember
import com.ttasjwi.board.system.common.auth.AuthMemberLoader
import com.ttasjwi.board.system.spring.security.authentication.AuthMemberAuthentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class AuthMemberLoaderImpl : AuthMemberLoader {

    override fun loadCurrentAuthMember(): AuthMember? {
        val authentication = SecurityContextHolder.getContext().authentication

        if (authentication is AuthMemberAuthentication) {
            return authentication.principal as AuthMember
        }
        return null
    }
}
