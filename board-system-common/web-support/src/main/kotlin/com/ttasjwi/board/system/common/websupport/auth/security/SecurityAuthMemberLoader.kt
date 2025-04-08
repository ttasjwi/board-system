package com.ttasjwi.board.system.common.websupport.auth.security

import com.ttasjwi.board.system.common.auth.AuthMember
import com.ttasjwi.board.system.common.auth.AuthMemberLoader
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder

class SecurityAuthMemberLoader : AuthMemberLoader {

    override fun loadCurrentAuthMember(): AuthMember? {
        val authentication = SecurityContextHolder.getContext().authentication
            ?: return null

        if (!authentication.isAuthenticated || authentication is AnonymousAuthenticationToken) {
            return null
        }
        return authentication.principal as AuthMember
    }
}
