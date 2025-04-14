package com.ttasjwi.board.system.common.infra.websupport.auth.security

import com.ttasjwi.board.system.common.auth.AuthMember
import com.ttasjwi.board.system.common.auth.AuthMemberLoader
import org.springframework.security.core.context.SecurityContextHolder

class SecurityAuthMemberLoader : AuthMemberLoader {

    override fun loadCurrentAuthMember(): AuthMember? {
        val authentication = SecurityContextHolder.getContextHolderStrategy()?.context?.authentication
            ?: return null

        if (authentication !is AuthMemberAuthentication) {
            return null
        }
        return authentication.principal as AuthMember
    }
}
