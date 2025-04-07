package com.ttasjwi.board.system.api.controller.member

import com.ttasjwi.board.system.application.member.usecase.EmailAvailableRequest
import com.ttasjwi.board.system.application.member.usecase.EmailAvailableResponse
import com.ttasjwi.board.system.application.member.usecase.EmailAvailableUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class EmailAvailableController(
    private val useCase: EmailAvailableUseCase,
) {

    @GetMapping("/api/v1/members/email-available")
    fun checkEmailAvailable(request: EmailAvailableRequest): ResponseEntity<EmailAvailableResponse> {
        return ResponseEntity.ok(useCase.checkEmailAvailable(request))
    }
}
