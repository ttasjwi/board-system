package com.ttasjwi.board.system.member.api

import com.ttasjwi.board.system.core.api.SuccessResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UsernameAvailableController {

    @GetMapping("/api/v1/members/username-available")
    fun checkUsernameAvailable(): ResponseEntity<SuccessResponse<UsernameAvailableResponse>> {
        TODO("Not yet implemented")
    }
}

data class UsernameAvailableResponse(
    val usernameAvailable: UsernameAvailable

) {
    data class UsernameAvailable(
        val username: String,
        val isAvailable: Boolean,
        val reasonCode: String,
        val message: String,
        val description: String
    )
}
