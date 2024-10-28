package com.ttasjwi.board.system.member.application.service

import com.ttasjwi.board.system.core.annotation.component.ApplicationService
import com.ttasjwi.board.system.member.application.usecase.NicknameAvailableRequest
import com.ttasjwi.board.system.member.application.usecase.NicknameAvailableResult
import com.ttasjwi.board.system.member.application.usecase.NicknameAvailableUseCase

@ApplicationService
internal class NicknameAvailableApplicationService : NicknameAvailableUseCase {

    override fun checkNicknameAvailable(request: NicknameAvailableRequest): NicknameAvailableResult {
        TODO("Not yet implemented")
    }
}
