package com.ttasjwi.board.system.common.infra.test.support.controller

import com.ttasjwi.board.system.common.annotation.auth.*
import com.ttasjwi.board.system.common.auth.AuthMember
import com.ttasjwi.board.system.common.auth.AuthMemberLoader
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthTestController(
    private val authMemberLoader: AuthMemberLoader,
) {

    @PermitAll
    @GetMapping("/api/v1/test/web-support/auth/permit-all")
    fun testPermitAll(): String {
        return "permit-all"
    }

    @RequireAuthenticated
    @GetMapping("/api/v1/test/web-support/auth/authenticated")
    fun testAuthenticated(): AuthTestResponse {
        val authMember = authMemberLoader.loadCurrentAuthMember()!!
        return AuthTestResponse.of("/api/v1/test/web-support/auth/authenticated", authMember)
    }

    @RequireAdminRole
    @GetMapping("/api/v1/test/web-support/auth/admin-role")
    fun testAdminRole(): AuthTestResponse {
        val authMember = authMemberLoader.loadCurrentAuthMember()!!
        return AuthTestResponse.of("/api/v1/test/web-support/auth/admin-role", authMember)
    }

    @RequireRootRole
    @GetMapping("/api/v1/test/web-support/auth/root-role")
    fun testRootRole(): AuthTestResponse {
        val authMember = authMemberLoader.loadCurrentAuthMember()!!
        return AuthTestResponse.of("/api/v1/test/web-support/auth/root-role", authMember)
    }
}

data class AuthTestResponse(
    val path: String,
    val memberId: Long,
    val role: String,
) {

    companion object {
        fun of(path: String, authMember: AuthMember): AuthTestResponse {
            return AuthTestResponse(
                path = path,
                memberId = authMember.memberId,
                role = authMember.role.name
            )
        }
    }
}
