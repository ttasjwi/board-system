package com.ttasjwi.board.system.member.application.service

import com.ttasjwi.board.system.core.annotation.component.ApplicationService
import com.ttasjwi.board.system.member.application.usecase.EmailAvailableRequest
import com.ttasjwi.board.system.member.application.usecase.EmailAvailableResult
import com.ttasjwi.board.system.member.application.usecase.EmailAvailableUseCase

@ApplicationService
internal class EmailAvailableApplicationService : EmailAvailableUseCase {

    override fun checkEmailAvailable(request: EmailAvailableRequest): EmailAvailableResult {
        TODO("Not yet implemented")
    }
}
