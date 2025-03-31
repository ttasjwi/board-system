package com.ttasjwi.board.system.member.application.usecase.fixture

import com.ttasjwi.board.system.member.application.usecase.UsernameAvailableRequest
import com.ttasjwi.board.system.member.application.usecase.UsernameAvailableResponse
import com.ttasjwi.board.system.member.application.usecase.UsernameAvailableUseCase

class UsernameAvailableUseCaseFixture : UsernameAvailableUseCase {

    override fun checkUsernameAvailable(request: UsernameAvailableRequest): UsernameAvailableResponse {
        return UsernameAvailableResponse(
            yourUsername = request.username!!,
            isAvailable = true,
            reasonCode = "UsernameAvailableCheck.Available",
            reasonMessage = "사용 가능한 사용자아이디(username)",
            reasonDescription = "이 사용자아이디(username)는 사용 가능합니다."
        )
    }
}
