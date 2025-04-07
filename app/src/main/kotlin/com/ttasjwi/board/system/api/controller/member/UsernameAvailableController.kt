package com.ttasjwi.board.system.api.controller.member

import com.ttasjwi.board.system.application.member.usecase.UsernameAvailableRequest
import com.ttasjwi.board.system.application.member.usecase.UsernameAvailableResponse
import com.ttasjwi.board.system.application.member.usecase.UsernameAvailableUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UsernameAvailableController(
    private val useCase: UsernameAvailableUseCase,
) {

    @GetMapping("/api/v1/members/username-available")
    fun checkUsernameAvailable(request: UsernameAvailableRequest): ResponseEntity<UsernameAvailableResponse> {
        return ResponseEntity.ok(useCase.checkUsernameAvailable(request))
    }
}
