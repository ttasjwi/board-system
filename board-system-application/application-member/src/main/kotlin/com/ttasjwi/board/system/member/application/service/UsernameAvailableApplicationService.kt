package com.ttasjwi.board.system.member.application.service

import com.ttasjwi.board.system.core.annotation.component.ApplicationService
import com.ttasjwi.board.system.member.application.usecase.UsernameAvailableRequest
import com.ttasjwi.board.system.member.application.usecase.UsernameAvailableResult
import com.ttasjwi.board.system.member.application.usecase.UsernameAvailableUseCase

@ApplicationService
internal class UsernameAvailableApplicationService : UsernameAvailableUseCase {

    override fun checkUsernameAvailable(request: UsernameAvailableRequest): UsernameAvailableResult {
        TODO("Not yet implemented")
    }
}
