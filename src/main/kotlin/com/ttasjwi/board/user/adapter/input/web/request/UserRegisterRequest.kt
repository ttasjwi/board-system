package com.ttasjwi.board.user.adapter.input.web.request

data class UserRegisterRequest (
    val loginId: String?,
    val nickname: String?,
    val email: String?,
    val password: String?,
)
