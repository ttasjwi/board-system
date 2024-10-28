package com.ttasjwi.board.system.member.api

import com.ttasjwi.board.system.core.api.SuccessResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class EmailAvailableController {

    @GetMapping("/api/v1/members/email-available")
    fun checkEmailAvailable(): ResponseEntity<SuccessResponse<EmailAvailableResponse>> {
        TODO("Not yet implemented")
    }
}

data class EmailAvailableResponse(
    val emailAvailable: EmailAvailable,
) {
    data class EmailAvailable(
        val email: String,
        val isAvailable: Boolean,
        val reasonCode: String,
        val message: String,
        val description: String,
    )
}
