package com.ttasjwi.board.system.board.application.service

import com.ttasjwi.board.system.board.application.mapper.BoardCreateCommandMapper
import com.ttasjwi.board.system.board.application.processor.BoardCreateProcessor
import com.ttasjwi.board.system.board.application.usecase.BoardCreateRequest
import com.ttasjwi.board.system.board.application.usecase.BoardCreateResult
import com.ttasjwi.board.system.board.application.usecase.BoardCreateUseCase
import com.ttasjwi.board.system.common.annotation.component.ApplicationService
import com.ttasjwi.board.system.common.application.TransactionRunner

@ApplicationService
internal class BoardCreateApplicationService(
    private val commandMapper: BoardCreateCommandMapper,
    private val processor: BoardCreateProcessor,
    private val transactionRunner: TransactionRunner,
) : BoardCreateUseCase {

    override fun createBoard(request: BoardCreateRequest): BoardCreateResult {
        val command = commandMapper.mapToCommand(request)

        return transactionRunner.run {
            processor.createBoard(command)
        }
    }
}
