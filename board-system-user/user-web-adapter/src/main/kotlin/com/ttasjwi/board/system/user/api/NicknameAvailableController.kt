package com.ttasjwi.board.system.user.api

import com.ttasjwi.board.system.common.annotation.auth.PermitAll
import com.ttasjwi.board.system.user.domain.NicknameAvailableRequest
import com.ttasjwi.board.system.user.domain.NicknameAvailableResponse
import com.ttasjwi.board.system.user.domain.NicknameAvailableUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class NicknameAvailableController(
    private val useCase: NicknameAvailableUseCase,
) {

    @PermitAll
    @GetMapping("/api/v1/users/nickname-available")
    fun checkNicknameAvailable(request: NicknameAvailableRequest): ResponseEntity<NicknameAvailableResponse> {
        return ResponseEntity.ok(useCase.checkNicknameAvailable(request))
    }
}
