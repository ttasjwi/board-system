package com.ttasjwi.board.system.member.domain.fixture

import com.ttasjwi.board.system.member.domain.EmailAvailableRequest
import com.ttasjwi.board.system.member.domain.EmailAvailableResponse
import com.ttasjwi.board.system.member.domain.EmailAvailableUseCase

class EmailAvailableUseCaseFixture : EmailAvailableUseCase {

    override fun checkEmailAvailable(request: EmailAvailableRequest): EmailAvailableResponse {
        return EmailAvailableResponse(
            yourEmail = request.email!!,
            isAvailable = true,
            reasonCode = "EmailAvailableCheck.Available",
            reasonMessage = "사용 가능한 이메일",
            reasonDescription = "이 이메일은 사용 가능합니다."
        )
    }
}
