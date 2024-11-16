package com.ttasjwi.board.system

import com.ttasjwi.board.system.auth.domain.model.AuthMember
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MvcTestController {

    @GetMapping("/api/v1/test/authenticated")
    fun testAuthenticated(): ResponseEntity<AuthMember> {
        val authMember =  SecurityContextHolder.getContext().authentication.principal as AuthMember
        return ResponseEntity.ok(authMember)
    }
}
