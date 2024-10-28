package com.ttasjwi.board.system.member.api

import com.ttasjwi.board.system.core.api.SuccessResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.time.ZonedDateTime

@RestController
class RegisterMemberController {

    @PostMapping("/api/v1/members")
    fun register(): ResponseEntity<SuccessResponse<RegisterMemberResponse>> {
        TODO("Not yet implemented")
    }
}

data class RegisterMemberResponse(
    val registeredMember: RegisteredMember
) {

    data class RegisteredMember(
        val memberId: Long,
        val email: String,
        val username: String,
        val nickname: String,
        val role: String,
        val registeredAt: ZonedDateTime,
    )
}
