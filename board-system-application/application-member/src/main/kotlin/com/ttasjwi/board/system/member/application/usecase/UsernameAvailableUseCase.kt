package com.ttasjwi.board.system.member.application.usecase

interface UsernameAvailableUseCase {
    /**
     * 전달받은 username(사용자 아이디)가 우리 서비스에서 새로 사용 가능한 username 인지 체크합니다.
     */
    fun checkUsernameAvailable(request: UsernameAvailableRequest): UsernameAvailableResult
}

data class UsernameAvailableRequest(
    val username: String?,
)

data class UsernameAvailableResult(
    val username: String,
    val isAvailable: Boolean,
    val reasonCode: String,
)
