package com.ttasjwi.board.system.user.domain.fixture

import com.ttasjwi.board.system.user.domain.NicknameAvailableRequest
import com.ttasjwi.board.system.user.domain.NicknameAvailableResponse
import com.ttasjwi.board.system.user.domain.NicknameAvailableUseCase

class NicknameAvailableUseCaseFixture : NicknameAvailableUseCase {

    override fun checkNicknameAvailable(request: NicknameAvailableRequest): NicknameAvailableResponse {
        return NicknameAvailableResponse(
            yourNickname = request.nickname!!,
            isAvailable = true,
            reasonCode = "NicknameAvailableCheck.Available",
            reasonMessage = "사용 가능한 닉네임",
            reasonDescription = "이 닉네임은 사용 가능합니다."
        )
    }
}
