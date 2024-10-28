package com.ttasjwi.board.system.member.api

import com.ttasjwi.board.system.core.api.SuccessResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class NicknameAvailableController {

    @GetMapping("/api/v1/members/nickname-available")
    fun checkNicknameAvailable(): ResponseEntity<SuccessResponse<NicknameAvailableResponse>> {
        TODO("Not yet implemented")
    }
}

data class NicknameAvailableResponse(
    val nicknameAvailable: NicknameAvailable
) {

    data class NicknameAvailable(
        val yourNickname: String,
        val isAvailable: Boolean,
        val reasonCode: String,
        val message: String,
        val description: String
    )
}
