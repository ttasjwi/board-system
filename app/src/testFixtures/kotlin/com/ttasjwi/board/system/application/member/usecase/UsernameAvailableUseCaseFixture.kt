package com.ttasjwi.board.system.application.member.usecase

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
