package com.ttasjwi.board.system.board.application.processor

import com.ttasjwi.board.system.board.application.dto.BoardCreateCommand
import com.ttasjwi.board.system.board.application.exception.DuplicateBoardNameException
import com.ttasjwi.board.system.board.application.exception.DuplicateBoardSlugException
import com.ttasjwi.board.system.board.application.usecase.BoardCreateResult
import com.ttasjwi.board.system.board.domain.model.Board
import com.ttasjwi.board.system.board.domain.service.BoardManager
import com.ttasjwi.board.system.board.domain.service.BoardStorage
import com.ttasjwi.board.system.core.annotation.component.ApplicationProcessor
import com.ttasjwi.board.system.logging.getLogger

@ApplicationProcessor
internal class BoardCreateProcessor(
    private val boardStorage: BoardStorage,
    private val boardManager: BoardManager,
) {

    companion object {
        private val log = getLogger(BoardCreateProcessor::class.java)
    }

    fun createBoard(command: BoardCreateCommand): BoardCreateResult {
        // 중복 체크
        checkDuplication(command)

        // 생성
        val board = makeBoard(command)

        // 저장
        val savedBoard = boardStorage.save(board)

        // 결과 생성, 반환
        return makeResult(savedBoard)
    }

    private fun checkDuplication(command: BoardCreateCommand) {
        // 게시판 이름이 중복되면 안 됨
        if (boardStorage.existsByName(command.boardName)) {
            val e = DuplicateBoardNameException(command.boardName.value)
            log.warn(e)
            throw e
        }

        // 게시판 슬러그가 중복되면 안 됨
        if (boardStorage.existsBySlug(command.boardSlug)) {
            val e = DuplicateBoardSlugException(command.boardSlug.value)
            log.warn(e)
            throw e
        }
    }

    private fun makeBoard(command: BoardCreateCommand): Board {
        return boardManager.create(
            name = command.boardName,
            description = command.boardDescription,
            slug = command.boardSlug,
            managerId = command.creator.memberId,
            currentTime = command.currentTime
        )
    }

    private fun makeResult(board: Board): BoardCreateResult {
        return BoardCreateResult(
            createdBoard = BoardCreateResult.CreatedBoard(
                boardId = board.id!!.value,
                name = board.name.value,
                description = board.description.value,
                managerId = board.managerId.value,
                slug = board.slug.value,
                createdAt = board.createdAt
            ),
        )
    }
}
