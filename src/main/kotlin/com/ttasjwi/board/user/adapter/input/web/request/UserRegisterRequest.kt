package com.ttasjwi.board.user.adapter.input.web.request

import com.ttasjwi.board.user.application.usecase.UserRegisterCommand

data class UserRegisterRequest(
    val loginId: String?,
    val nickname: String?,
    val email: String?,
    val password: String?,
) {
    fun toCommand() = UserRegisterCommand.of(
        loginId = loginId,
        nickname = nickname,
        email = email,
        password = password
    )

}
