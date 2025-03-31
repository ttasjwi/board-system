package com.ttasjwi.board.system.member.application.usecase

import java.time.ZonedDateTime

interface RegisterMemberUseCase {

    /**
     * 회원 가입을 수행합니다.
     */
    fun register(request: RegisterMemberRequest): RegisterMemberResponse
}

data class RegisterMemberRequest(
    val email: String?,
    val password: String?,
    val username: String?,
    val nickname: String?,
)

data class RegisterMemberResponse(
    val memberId: Long,
    val email: String,
    val username: String,
    val nickname: String,
    val role: String,
    val registeredAt: ZonedDateTime,
)
