package com.ttasjwi.board.system.api.controller.auth

import com.ttasjwi.board.system.application.auth.usecase.LoginRequest
import com.ttasjwi.board.system.application.auth.usecase.LoginResponse
import com.ttasjwi.board.system.application.auth.usecase.LoginUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginController(
    private val useCase: LoginUseCase,
) {

    @PostMapping("/api/v1/auth/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<LoginResponse> {
        return ResponseEntity.ok(useCase.login(request))
    }
}
