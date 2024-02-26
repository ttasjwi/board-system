package com.ttasjwi.board.user.adapter.input.web

import com.ttasjwi.board.user.adapter.input.web.request.UserRegisterRequest
import com.ttasjwi.board.user.adapter.input.web.response.UserRegisterResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {

    /**
     * 이 엔드포인트에서는 회원가입을 처리합니다.
     */
    @PostMapping("/api/users")
    fun registerUser(@RequestBody request: UserRegisterRequest): ResponseEntity<UserRegisterResponse> {

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                UserRegisterResponse(
                    id = 1L,
                    loginId = request.loginId ?: "loginId",
                    nickname = request.nickname ?: "nickname",
                    email = request.email ?: "email@gmail.com",
                    role = "USER"
                )
            )
    }

}
