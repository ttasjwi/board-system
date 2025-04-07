package com.ttasjwi.board.system.application.member.usecase

interface UsernameAvailableUseCase {
    /**
     * 전달받은 username(사용자 아이디)가 우리 서비스에서 새로 사용 가능한 username 인지 체크합니다.
     */
    fun checkUsernameAvailable(request: UsernameAvailableRequest): UsernameAvailableResponse
}

data class UsernameAvailableRequest(
    val username: String?,
)

data class UsernameAvailableResponse(
    val yourUsername: String,
    val isAvailable: Boolean,
    val reasonCode: String,
    val reasonMessage: String,
    val reasonDescription: String,
)
