package com.ttasjwi.board.user.adapter.input.web

import com.ttasjwi.board.user.adapter.input.web.request.UserRegisterRequest
import com.ttasjwi.board.user.adapter.input.web.response.UserRegisterResponse
import com.ttasjwi.board.user.application.usecase.UserRegisterUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userRegisterUseCase: UserRegisterUseCase
) {

    /**
     * 이 엔드포인트에서는 회원가입을 처리합니다.
     */
    @PostMapping("/api/users")
    fun registerUser(@RequestBody request: UserRegisterRequest): ResponseEntity<UserRegisterResponse> {
        val command = request.toCommand()
        val result = userRegisterUseCase.register(command)
        val response = UserRegisterResponse.from(result)

        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

}
