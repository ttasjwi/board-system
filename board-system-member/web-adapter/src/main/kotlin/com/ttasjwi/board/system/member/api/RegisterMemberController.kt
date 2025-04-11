package com.ttasjwi.board.system.member.api

import com.ttasjwi.board.system.common.annotation.auth.PermitAll
import com.ttasjwi.board.system.member.domain.RegisterMemberRequest
import com.ttasjwi.board.system.member.domain.RegisterMemberResponse
import com.ttasjwi.board.system.member.domain.RegisterMemberUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class RegisterMemberController(
    private val useCase: RegisterMemberUseCase,
) {

    @PermitAll
    @PostMapping("/api/v1/members")
    fun register(@RequestBody request: RegisterMemberRequest): ResponseEntity<RegisterMemberResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(useCase.register(request))
    }
}
