package com.ttasjwi.board.system.member.application.usecase

interface EmailAvailableUseCase {
    fun checkEmailAvailable(request: EmailAvailableRequest): EmailAvailableResponse
}

data class EmailAvailableRequest(
    val email: String?
)

data class EmailAvailableResponse(
    val yourEmail: String,
    val isAvailable: Boolean,
    val reasonCode: String,
    val reasonMessage: String,
    val reasonDescription: String,
)
