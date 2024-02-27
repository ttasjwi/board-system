package com.ttasjwi.board.user.application.usecase

interface UserRegisterUseCase {
    fun register(command: UserRegisterCommand) : UserRegisterResult
}

data class UserRegisterCommand(
    val loginId: String,
    val nickname: String,
    val email: String,
    val password: String,
) {

    companion object {
        fun of(loginId: String, nickname: String, email:String, password: String): UserRegisterCommand {
            // TODO: 입력값 검증
            return UserRegisterCommand(loginId, nickname, email, password)
        }
    }
}

data class UserRegisterResult (
    val id: Long,
    val loginId: String,
    val nickname: String,
    val email: String,
    val role: String,
)
