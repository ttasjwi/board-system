package com.ttasjwi.board.system.common.infra.websupport.auth.security

import com.ttasjwi.board.system.common.auth.AuthMemberLoader
import com.ttasjwi.board.system.common.auth.Role
import com.ttasjwi.board.system.common.infra.websupport.auth.exception.AccessDeniedException
import com.ttasjwi.board.system.common.infra.websupport.auth.exception.UnauthenticatedException
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before

@Aspect
class MethodAuthorizationAspect(
    private val authMemberLoader: AuthMemberLoader
) {

    companion object {
        private val ADMIN_ROLES = listOf(Role.ADMIN, Role.ROOT)
    }

    @Before("@annotation(com.ttasjwi.board.system.common.annotation.auth.PermitAll)")
    fun checkPermitAll() {

    }

    @Before("@annotation(com.ttasjwi.board.system.common.annotation.auth.RequireAuthenticated)")
    fun checkAuthenticated() {
        authMemberLoader.loadCurrentAuthMember() ?: throw UnauthenticatedException()
    }

    @Before("@annotation(com.ttasjwi.board.system.common.annotation.auth.RequireAdminRole)")
    fun checkAdminRole() {
        val authMember = authMemberLoader.loadCurrentAuthMember() ?: throw UnauthenticatedException()

        if (authMember.role !in ADMIN_ROLES) {
            throw AccessDeniedException()
        }
    }

    @Before("@annotation(com.ttasjwi.board.system.common.annotation.auth.RequireRootRole)")
    fun checkRootRole() {
        val authMember = authMemberLoader.loadCurrentAuthMember() ?: throw UnauthenticatedException()

        if (authMember.role != Role.ROOT) {
            throw AccessDeniedException()
        }
    }
}
