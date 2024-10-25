package com.ttasjwi.board.system.member.application.usecase

interface NicknameAvailableUseCase {
    /**
     * 전달받은 닉네임이 우리 서비스에서 새로 사용 가능한 닉네임인지 체크합니다.
     */
    fun checkNicknameAvailable(request: NicknameAvailableRequest): NicknameAvailableResult
}

data class NicknameAvailableRequest(
    val nickname: String?
)

class NicknameAvailableResult(
    val nickname: String,
    val isAvailable: Boolean,
    val reasonCode: String,
)
