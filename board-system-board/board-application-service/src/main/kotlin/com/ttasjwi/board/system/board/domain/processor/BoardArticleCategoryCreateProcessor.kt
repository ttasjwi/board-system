package com.ttasjwi.board.system.board.domain.processor

import com.ttasjwi.board.system.board.domain.dto.BoardArticleCategoryCreateCommand
import com.ttasjwi.board.system.board.domain.exception.BoardNotFoundException
import com.ttasjwi.board.system.board.domain.exception.DuplicateBoardArticleCategoryNameException
import com.ttasjwi.board.system.board.domain.exception.DuplicateBoardArticleCategorySlugException
import com.ttasjwi.board.system.board.domain.exception.NoBoardArticleCategoryCreateAuthorityException
import com.ttasjwi.board.system.board.domain.model.Board
import com.ttasjwi.board.system.board.domain.model.BoardArticleCategory
import com.ttasjwi.board.system.board.domain.port.BoardArticleCategoryPersistencePort
import com.ttasjwi.board.system.board.domain.port.BoardPersistencePort
import com.ttasjwi.board.system.common.annotation.component.ApplicationProcessor
import com.ttasjwi.board.system.common.auth.AuthMember
import com.ttasjwi.board.system.common.idgenerator.IdGenerator
import org.springframework.transaction.annotation.Transactional

@ApplicationProcessor
class BoardArticleCategoryCreateProcessor(
    private val boardPersistencePort: BoardPersistencePort,
    private val boardArticleCategoryPersistencePort: BoardArticleCategoryPersistencePort,
) {

    private val boardArticleCategoryIdGenerator: IdGenerator = IdGenerator.create()

    @Transactional
    fun create(command: BoardArticleCategoryCreateCommand): BoardArticleCategory {
        // 게시판 조회
        val board = getBoardOrThrow(command.boardId)

        // 권한 체크
        checkBoardCreateAuthority(board, command.creator)

        // 중복 체크
        checkDuplication(command)

        // 생성, 저장
        val boardArticleCategory =  createBoardArticleCategoryAndPersist(command)

        return boardArticleCategory
    }

    private fun getBoardOrThrow(boardId: Long): Board {
        return boardPersistencePort.findByIdOrNull(boardId)
            ?: throw BoardNotFoundException(
                source = "boardId",
                argument = boardId
            )
    }

    private fun checkBoardCreateAuthority(board: Board, creator: AuthMember) {
        if (board.managerId != creator.memberId) {
            throw NoBoardArticleCategoryCreateAuthorityException(
                boardManagerId = board.managerId,
                creatorId = creator.memberId
            )
        }
    }

    private fun checkDuplication(command: BoardArticleCategoryCreateCommand) {
        // 카테고리 이름 :  역시 그 게시판 내에서 중복되선 안 된다.
        if (boardArticleCategoryPersistencePort.existsByName(command.boardId, command.boardArticleCategoryName)) {
            throw DuplicateBoardArticleCategoryNameException(command.boardArticleCategoryName)
        }
        // 카테고리 슬러그 : 그 게시판 내에서 중복되선 안 된다.
        if (boardArticleCategoryPersistencePort.existsBySlug(command.boardId, command.boardArticleCategorySlug)) {
            throw DuplicateBoardArticleCategorySlugException(command.boardArticleCategorySlug)
        }
    }

    private fun createBoardArticleCategoryAndPersist(command: BoardArticleCategoryCreateCommand): BoardArticleCategory {
        val boardArticleCategory = BoardArticleCategory.create(
            boardArticleCategoryId = boardArticleCategoryIdGenerator.nextId(),
            boardId = command.boardId,
            name = command.boardArticleCategoryName,
            slug = command.boardArticleCategorySlug,
            allowSelfDelete = command.allowSelfDelete,
            allowLike = command.allowLike,
            allowDislike = command.allowDislike,
            createdAt = command.currentTime
        )
        boardArticleCategoryPersistencePort.save(boardArticleCategory)
        return boardArticleCategory
    }
}
