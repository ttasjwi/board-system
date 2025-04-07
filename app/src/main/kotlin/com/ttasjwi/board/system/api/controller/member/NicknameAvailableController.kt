package com.ttasjwi.board.system.api.controller.member

import com.ttasjwi.board.system.application.member.usecase.NicknameAvailableRequest
import com.ttasjwi.board.system.application.member.usecase.NicknameAvailableResponse
import com.ttasjwi.board.system.application.member.usecase.NicknameAvailableUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class NicknameAvailableController(
    private val useCase: NicknameAvailableUseCase,
) {

    @GetMapping("/api/v1/members/nickname-available")
    fun checkNicknameAvailable(request: NicknameAvailableRequest): ResponseEntity<NicknameAvailableResponse> {
        return ResponseEntity.ok(useCase.checkNicknameAvailable(request))
    }
}
