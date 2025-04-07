package com.ttasjwi.board.system.application.board.service

import com.ttasjwi.board.system.application.board.mapper.BoardCreateCommandMapper
import com.ttasjwi.board.system.application.board.processor.BoardCreateProcessor
import com.ttasjwi.board.system.application.board.usecase.BoardCreateRequest
import com.ttasjwi.board.system.application.board.usecase.BoardCreateResponse
import com.ttasjwi.board.system.application.board.usecase.BoardCreateUseCase
import com.ttasjwi.board.system.global.annotation.ApplicationService

@ApplicationService
internal class BoardCreateApplicationService(
    private val commandMapper: BoardCreateCommandMapper,
    private val processor: BoardCreateProcessor,
) : BoardCreateUseCase {

    override fun createBoard(request: BoardCreateRequest): BoardCreateResponse {
        val command = commandMapper.mapToCommand(request)

        return processor.createBoard(command)
    }
}
