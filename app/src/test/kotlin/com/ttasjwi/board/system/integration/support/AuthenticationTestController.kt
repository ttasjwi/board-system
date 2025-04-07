package com.ttasjwi.board.system.integration.support

import com.ttasjwi.board.system.common.auth.AuthMember
import com.ttasjwi.board.system.common.auth.AuthMemberLoader
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 인증 기능 동작 확인용 컨트롤러
 */
@RestController
class AuthenticationTestController(
    private val authMemberLoader: AuthMemberLoader
) {

    @GetMapping("/api/v1/test/authenticated")
    fun testAuthenticated(): ResponseEntity<AuthMember> {
        val authMember = authMemberLoader.loadCurrentAuthMember()!!
        return ResponseEntity.ok(authMember)
    }
}
