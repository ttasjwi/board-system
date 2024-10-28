package com.ttasjwi.board.system.member.application.usecase

interface EmailAvailableUseCase {
    fun checkEmailAvailable(request: EmailAvailableRequest): EmailAvailableResult
}

data class EmailAvailableRequest(
    val email: String?
)

data class EmailAvailableResult(
    val email: String,
    val isAvailable: Boolean,
    val reasonCode: String,
)
