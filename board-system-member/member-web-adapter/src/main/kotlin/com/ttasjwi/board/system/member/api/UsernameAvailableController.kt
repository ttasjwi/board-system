package com.ttasjwi.board.system.member.api

import com.ttasjwi.board.system.common.annotation.auth.PermitAll
import com.ttasjwi.board.system.member.domain.UsernameAvailableRequest
import com.ttasjwi.board.system.member.domain.UsernameAvailableResponse
import com.ttasjwi.board.system.member.domain.UsernameAvailableUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UsernameAvailableController(
    private val useCase: UsernameAvailableUseCase,
) {

    @PermitAll
    @GetMapping("/api/v1/members/username-available")
    fun checkUsernameAvailable(request: UsernameAvailableRequest): ResponseEntity<UsernameAvailableResponse> {
        return ResponseEntity.ok(useCase.checkUsernameAvailable(request))
    }
}
