package com.ttasjwi.board.system.member.application.usecase.fixture

import com.ttasjwi.board.system.member.application.usecase.*

class NicknameAvailableUseCaseFixture : NicknameAvailableUseCase {

    override fun checkNicknameAvailable(request: NicknameAvailableRequest): NicknameAvailableResult {
        return NicknameAvailableResult(request.nickname!!, true, "NicknameAvailableCheck.Available")
    }
}
