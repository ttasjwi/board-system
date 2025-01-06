package com.ttasjwi.board.system.board.api

import com.ttasjwi.board.system.board.application.usecase.BoardCreateRequest
import com.ttasjwi.board.system.board.application.usecase.BoardCreateResult
import com.ttasjwi.board.system.board.application.usecase.BoardCreateUseCase
import com.ttasjwi.board.system.core.api.SuccessResponse
import com.ttasjwi.board.system.core.locale.LocaleManager
import com.ttasjwi.board.system.core.message.MessageResolver
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class BoardCreateController(
    private val useCase: BoardCreateUseCase,
    private val messageResolver: MessageResolver,
    private val localeManager: LocaleManager,
) {

    @PostMapping("/api/v1/boards")
    fun createBoard(@RequestBody request: BoardCreateRequest): ResponseEntity<SuccessResponse<BoardCreateResult>> {
        // 유즈케이스에 요청 처리를 위임
        val result = useCase.createBoard(request)

        // 처리 결과로부터 응답 메시지 가공
        val response = makeResponse(result)

        // 201 상태코드와 함께 HTTP 응답
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    private fun makeResponse(result: BoardCreateResult): SuccessResponse<BoardCreateResult> {
        val code = "BoardCreate.Complete"
        val locale = localeManager.getCurrentLocale()
        return SuccessResponse(
            code = code,
            message = messageResolver.resolve("$code.message", locale),
            description = messageResolver.resolve("$code.description", locale),
            data = result
        )
    }
}
