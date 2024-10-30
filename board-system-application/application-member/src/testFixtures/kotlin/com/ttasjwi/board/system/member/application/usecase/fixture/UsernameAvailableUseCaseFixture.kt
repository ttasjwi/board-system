package com.ttasjwi.board.system.member.application.usecase.fixture

import com.ttasjwi.board.system.member.application.usecase.UsernameAvailableRequest
import com.ttasjwi.board.system.member.application.usecase.UsernameAvailableResult
import com.ttasjwi.board.system.member.application.usecase.UsernameAvailableUseCase

class UsernameAvailableUseCaseFixture : UsernameAvailableUseCase {

    override fun checkUsernameAvailable(request: UsernameAvailableRequest): UsernameAvailableResult {
        return UsernameAvailableResult(request.username!!, true, "UsernameAvailableCheck.Available")
    }
}
