package com.ttasjwi.board.user.adapter.input.web.response

import com.ttasjwi.board.user.application.usecase.UserRegisterResult

data class UserRegisterResponse (
    val id: Long,
    val loginId: String,
    val nickname: String,
    val email: String,
    val role: String,
) {
    companion object {

        fun from(result: UserRegisterResult) = UserRegisterResponse(
            id = result.id,
            loginId = result.loginId,
            nickname = result.nickname,
            email = result.email,
            role = result.role
        )
    }
}
