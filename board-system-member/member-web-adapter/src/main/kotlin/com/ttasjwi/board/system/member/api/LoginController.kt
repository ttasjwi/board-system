package com.ttasjwi.board.system.member.api

import com.ttasjwi.board.system.common.annotation.auth.PermitAll
import com.ttasjwi.board.system.member.domain.LoginRequest
import com.ttasjwi.board.system.member.domain.LoginResponse
import com.ttasjwi.board.system.member.domain.LoginUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginController(
    private val useCase: LoginUseCase,
) {

    @PermitAll
    @PostMapping("/api/v1/auth/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<LoginResponse> {
        return ResponseEntity.ok(useCase.login(request))
    }
}
