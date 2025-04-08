package com.ttasjwi.board.system.member.api

import com.ttasjwi.board.system.member.application.usecase.RegisterMemberRequest
import com.ttasjwi.board.system.member.application.usecase.RegisterMemberResponse
import com.ttasjwi.board.system.member.application.usecase.RegisterMemberUseCase
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
