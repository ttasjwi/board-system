package com.ttasjwi.board.system.board.domain.processor

import com.ttasjwi.board.system.board.domain.BoardCreateResponse
import com.ttasjwi.board.system.board.domain.dto.BoardCreateCommand
import com.ttasjwi.board.system.board.domain.exception.DuplicateBoardNameException
import com.ttasjwi.board.system.board.domain.exception.DuplicateBoardSlugException
import com.ttasjwi.board.system.board.domain.model.Board
import com.ttasjwi.board.system.board.domain.port.BoardPersistencePort
import com.ttasjwi.board.system.common.annotation.component.ApplicationProcessor
import com.ttasjwi.board.system.common.idgenerator.IdGenerator
import com.ttasjwi.board.system.common.logger.getLogger
import org.springframework.transaction.annotation.Transactional

@ApplicationProcessor
internal class BoardCreateProcessor(
    private val boardPersistencePort: BoardPersistencePort,
) {

    private val boardIdGenerator: IdGenerator = IdGenerator.create()

    companion object {
        private val log = getLogger(BoardCreateProcessor::class.java)
    }

    @Transactional
    fun createBoard(command: BoardCreateCommand): BoardCreateResponse {
        // 중복 체크
        checkDuplication(command)

        // 생성
        val board = makeBoard(command)

        // 저장
        val savedBoard = boardPersistencePort.save(board)

        // 결과 생성, 반환
        return makeResponse(savedBoard)
    }

    private fun checkDuplication(command: BoardCreateCommand) {
        // 게시판 이름이 중복되면 안 됨
        if (boardPersistencePort.existsByName(command.boardName)) {
            val e = DuplicateBoardNameException(command.boardName)
            log.warn(e)
            throw e
        }

        // 게시판 슬러그가 중복되면 안 됨
        if (boardPersistencePort.existsBySlug(command.boardSlug)) {
            val e = DuplicateBoardSlugException(command.boardSlug)
            log.warn(e)
            throw e
        }
    }

    private fun makeBoard(command: BoardCreateCommand): Board {
        return Board.create(
            boardId = boardIdGenerator.nextId(),
            name = command.boardName,
            description = command.boardDescription,
            slug = command.boardSlug,
            managerId = command.creator.userId,
            currentTime = command.currentTime
        )
    }

    private fun makeResponse(board: Board): BoardCreateResponse {
        return BoardCreateResponse(
            boardId = board.boardId.toString(),
            name = board.name,
            description = board.description,
            managerId = board.managerId.toString(),
            slug = board.slug,
            createdAt = board.createdAt.toZonedDateTime()
        )
    }
}
