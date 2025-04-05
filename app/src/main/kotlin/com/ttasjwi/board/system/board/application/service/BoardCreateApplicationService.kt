package com.ttasjwi.board.system.board.application.service

import com.ttasjwi.board.system.board.application.mapper.BoardCreateCommandMapper
import com.ttasjwi.board.system.board.application.processor.BoardCreateProcessor
import com.ttasjwi.board.system.board.application.usecase.BoardCreateRequest
import com.ttasjwi.board.system.board.application.usecase.BoardCreateResponse
import com.ttasjwi.board.system.board.application.usecase.BoardCreateUseCase
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
