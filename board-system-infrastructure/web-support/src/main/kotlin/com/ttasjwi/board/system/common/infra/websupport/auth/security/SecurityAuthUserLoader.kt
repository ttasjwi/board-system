package com.ttasjwi.board.system.common.infra.websupport.auth.security

import com.ttasjwi.board.system.common.auth.AuthUser
import com.ttasjwi.board.system.common.auth.AuthUserLoader
import org.springframework.security.core.context.SecurityContextHolder

class SecurityAuthUserLoader : AuthUserLoader {

    override fun loadCurrentAuthUser(): AuthUser? {
        val authentication = SecurityContextHolder.getContextHolderStrategy()?.context?.authentication
            ?: return null

        if (authentication !is AuthMemberAuthentication) {
            return null
        }
        return authentication.principal as AuthUser
    }
}
