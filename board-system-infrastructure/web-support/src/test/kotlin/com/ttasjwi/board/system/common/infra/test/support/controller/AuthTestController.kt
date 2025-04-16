package com.ttasjwi.board.system.common.infra.test.support.controller

import com.ttasjwi.board.system.common.annotation.auth.*
import com.ttasjwi.board.system.common.auth.AuthUser
import com.ttasjwi.board.system.common.auth.AuthUserLoader
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthTestController(
    private val authUserLoader: AuthUserLoader,
) {

    @PermitAll
    @GetMapping("/api/v1/test/web-support/auth/permit-all")
    fun testPermitAll(): String {
        return "permit-all"
    }

    @RequireAuthenticated
    @GetMapping("/api/v1/test/web-support/auth/authenticated")
    fun testAuthenticated(): AuthTestResponse {
        val authUser = authUserLoader.loadCurrentAuthUser()!!
        return AuthTestResponse.of("/api/v1/test/web-support/auth/authenticated", authUser)
    }

    @RequireAdminRole
    @GetMapping("/api/v1/test/web-support/auth/admin-role")
    fun testAdminRole(): AuthTestResponse {
        val authUser = authUserLoader.loadCurrentAuthUser()!!
        return AuthTestResponse.of("/api/v1/test/web-support/auth/admin-role", authUser)
    }

    @RequireRootRole
    @GetMapping("/api/v1/test/web-support/auth/root-role")
    fun testRootRole(): AuthTestResponse {
        val authUser = authUserLoader.loadCurrentAuthUser()!!
        return AuthTestResponse.of("/api/v1/test/web-support/auth/root-role", authUser)
    }
}

data class AuthTestResponse(
    val path: String,
    val userId: Long,
    val role: String,
) {

    companion object {
        fun of(path: String, authUser: AuthUser): AuthTestResponse {
            return AuthTestResponse(
                path = path,
                userId = authUser.userId,
                role = authUser.role.name
            )
        }
    }
}
