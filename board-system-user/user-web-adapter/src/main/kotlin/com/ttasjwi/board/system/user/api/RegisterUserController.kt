package com.ttasjwi.board.system.user.api

import com.ttasjwi.board.system.common.annotation.auth.PermitAll
import com.ttasjwi.board.system.user.domain.RegisterUserRequest
import com.ttasjwi.board.system.user.domain.RegisterUserResponse
import com.ttasjwi.board.system.user.domain.RegisterUserUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class RegisterUserController(
    private val useCase: RegisterUserUseCase,
) {

    @PermitAll
    @PostMapping("/api/v1/users")
    fun register(@RequestBody request: RegisterUserRequest): ResponseEntity<RegisterUserResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(useCase.register(request))
    }
}
