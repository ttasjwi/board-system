package com.ttasjwi.board.system.user.domain

import java.time.ZonedDateTime

interface RegisterUserUseCase {

    /**
     * 회원 가입을 수행합니다.
     */
    fun register(request: RegisterUserRequest): RegisterUserResponse
}

data class RegisterUserRequest(
    val email: String?,
    val password: String?,
    val username: String?,
    val nickname: String?,
)

data class RegisterUserResponse(
    val userId: String,
    val email: String,
    val username: String,
    val nickname: String,
    val role: String,
    val registeredAt: ZonedDateTime,
)
