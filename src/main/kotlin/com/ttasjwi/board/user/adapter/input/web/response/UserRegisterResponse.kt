package com.ttasjwi.board.user.adapter.input.web.response

data class UserRegisterResponse (
    val id: Long,
    val loginId: String,
    val nickname: String,
    val email: String,
    val role: String,
)
