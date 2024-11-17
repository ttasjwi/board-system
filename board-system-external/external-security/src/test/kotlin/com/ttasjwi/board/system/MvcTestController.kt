package com.ttasjwi.board.system

import com.ttasjwi.board.system.auth.domain.model.AuthMember
import com.ttasjwi.board.system.auth.domain.service.AuthMemberLoader
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MvcTestController(
    private val authMemberLoader: AuthMemberLoader
) {

    @GetMapping("/api/v1/test/authenticated")
    fun testAuthenticated(): ResponseEntity<AuthMember> {
        val authMember = authMemberLoader.loadCurrentAuthMember()!!
        return ResponseEntity.ok(authMember)
    }
}
