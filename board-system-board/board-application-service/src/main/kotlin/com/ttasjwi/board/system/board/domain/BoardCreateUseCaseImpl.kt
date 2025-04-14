package com.ttasjwi.board.system.board.domain

import com.ttasjwi.board.system.board.domain.mapper.BoardCreateCommandMapper
import com.ttasjwi.board.system.board.domain.processor.BoardCreateProcessor
import com.ttasjwi.board.system.common.annotation.component.UseCase

@UseCase
internal class BoardCreateUseCaseImpl(
    private val commandMapper: BoardCreateCommandMapper,
    private val processor: BoardCreateProcessor,
) : BoardCreateUseCase {

    override fun createBoard(request: BoardCreateRequest): BoardCreateResponse {
        val command = commandMapper.mapToCommand(request)

        return processor.createBoard(command)
    }
}
