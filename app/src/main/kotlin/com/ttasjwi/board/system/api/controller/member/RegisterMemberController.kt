package com.ttasjwi.board.system.api.controller.member

import com.ttasjwi.board.system.application.member.usecase.RegisterMemberRequest
import com.ttasjwi.board.system.application.member.usecase.RegisterMemberResponse
import com.ttasjwi.board.system.application.member.usecase.RegisterMemberUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class RegisterMemberController(
    private val useCase: RegisterMemberUseCase,
) {

    @PostMapping("/api/v1/members")
    fun register(@RequestBody request: RegisterMemberRequest): ResponseEntity<RegisterMemberResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(useCase.register(request))
    }
}
