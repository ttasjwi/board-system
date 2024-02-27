package com.ttasjwi.board.user.adapter.input.web.request

import com.ttasjwi.board.user.application.usecase.UserRegisterCommand

data class UserRegisterRequest(
    val loginId: String?,
    val nickname: String?,
    val email: String?,
    val password: String?,
) {

    fun toCommand(): UserRegisterCommand {
        validate()
        return UserRegisterCommand.of(
            loginId = loginId!!,
            nickname = nickname!!,
            email = email!!,
            password = password!!
        )
    }

    private fun validate() {
        if (loginId === null) {
            throw IllegalArgumentException("로그인 id는 null일 수 없습니다.")
        }
        if (nickname === null) {
            throw IllegalArgumentException("닉네임은 null일 수 없습니다.")
        }
        if (email === null) {
            throw IllegalArgumentException("email은 null일 수 없습니다.")
        }
        if (password === null) {
            throw IllegalArgumentException("비밀번호는 null일 수 없습니다.")
        }
    }
}
