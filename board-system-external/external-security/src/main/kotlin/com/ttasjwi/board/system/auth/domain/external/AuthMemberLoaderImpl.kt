package com.ttasjwi.board.system.auth.domain.external

import com.ttasjwi.board.system.auth.domain.model.AuthMember
import com.ttasjwi.board.system.auth.domain.service.AuthMemberLoader
import com.ttasjwi.board.system.external.spring.security.authentication.AuthMemberAuthentication
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
