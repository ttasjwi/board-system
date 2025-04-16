package com.ttasjwi.board.system.user.api

import com.ttasjwi.board.system.common.annotation.auth.PermitAll
import com.ttasjwi.board.system.user.domain.EmailAvailableRequest
import com.ttasjwi.board.system.user.domain.EmailAvailableResponse
import com.ttasjwi.board.system.user.domain.EmailAvailableUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class EmailAvailableController(
    private val useCase: EmailAvailableUseCase,
) {

    @PermitAll
    @GetMapping("/api/v1/users/email-available")
    fun checkEmailAvailable(request: EmailAvailableRequest): ResponseEntity<EmailAvailableResponse> {
        return ResponseEntity.ok(useCase.checkEmailAvailable(request))
    }
}
