package com.ttasjwi.board.system.auth.api

import com.ttasjwi.board.system.auth.application.usecase.LoginRequest
import com.ttasjwi.board.system.auth.application.usecase.LoginResponse
import com.ttasjwi.board.system.auth.application.usecase.LoginUseCase
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
