package com.ttasjwi.board.system.member.application.usecase.fixture

import com.ttasjwi.board.system.member.application.usecase.EmailAvailableRequest
import com.ttasjwi.board.system.member.application.usecase.EmailAvailableResult
import com.ttasjwi.board.system.member.application.usecase.EmailAvailableUseCase

class EmailAvailableUseCaseFixture : EmailAvailableUseCase {

    override fun checkEmailAvailable(request: EmailAvailableRequest): EmailAvailableResult {
        return EmailAvailableResult(request.email!!, true, "EmailAvailableCheck.Available")
    }
}
