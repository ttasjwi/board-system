package com.ttasjwi.board.system.board.api

import com.ttasjwi.board.system.board.domain.BoardCreateRequest
import com.ttasjwi.board.system.board.domain.BoardCreateResponse
import com.ttasjwi.board.system.board.domain.BoardCreateUseCase
import com.ttasjwi.board.system.common.annotation.auth.RequireAuthenticated
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class BoardCreateController(
    private val useCase: BoardCreateUseCase,
) {

    @RequireAuthenticated
    @PostMapping("/api/v1/boards")
    fun createBoard(@RequestBody request: BoardCreateRequest): ResponseEntity<BoardCreateResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(useCase.createBoard(request))
    }
}
