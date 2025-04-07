package com.ttasjwi.board.system.api.controller.board

import com.ttasjwi.board.system.board.application.usecase.BoardCreateRequest
import com.ttasjwi.board.system.board.application.usecase.BoardCreateResponse
import com.ttasjwi.board.system.board.application.usecase.BoardCreateUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class BoardCreateController(
    private val useCase: BoardCreateUseCase,
) {

    @PostMapping("/api/v1/boards")
    fun createBoard(@RequestBody request: BoardCreateRequest): ResponseEntity<BoardCreateResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(useCase.createBoard(request))
    }
}
