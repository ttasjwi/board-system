package com.ttasjwi.board.system.board.domain.processor

import com.ttasjwi.board.system.board.domain.dto.ArticleCategoryCreateCommand
import com.ttasjwi.board.system.board.domain.exception.BoardNotFoundException
import com.ttasjwi.board.system.board.domain.exception.DuplicateArticleCategoryNameException
import com.ttasjwi.board.system.board.domain.exception.DuplicateArticleCategorySlugException
import com.ttasjwi.board.system.board.domain.exception.NoArticleCategoryCreateAuthorityException
import com.ttasjwi.board.system.board.domain.model.Board
import com.ttasjwi.board.system.board.domain.model.ArticleCategory
import com.ttasjwi.board.system.board.domain.port.ArticleCategoryPersistencePort
import com.ttasjwi.board.system.board.domain.port.BoardPersistencePort
import com.ttasjwi.board.system.common.annotation.component.ApplicationProcessor
import com.ttasjwi.board.system.common.auth.AuthUser
import com.ttasjwi.board.system.common.idgenerator.IdGenerator
import org.springframework.transaction.annotation.Transactional

@ApplicationProcessor
class ArticleCategoryCreateProcessor(
    private val boardPersistencePort: BoardPersistencePort,
    private val articleCategoryPersistencePort: ArticleCategoryPersistencePort,
) {

    private val articleCategoryIdGenerator: IdGenerator = IdGenerator.create()

    @Transactional
    fun create(command: ArticleCategoryCreateCommand): ArticleCategory {
        // 게시판 조회
        val board = getBoardOrThrow(command.boardId)

        // 권한 체크
        checkAuthority(board, command.creator)

        // 중복 체크
        checkDuplication(command)

        // 생성, 저장
        val articleCategory =  createArticleCategoryAndPersist(command)

        return articleCategory
    }

    private fun getBoardOrThrow(boardId: Long): Board {
        return boardPersistencePort.findByIdOrNull(boardId)
            ?: throw BoardNotFoundException(
                source = "boardId",
                argument = boardId
            )
    }

    private fun checkAuthority(board: Board, creator: AuthUser) {
        if (board.managerId != creator.userId) {
            throw NoArticleCategoryCreateAuthorityException(
                boardManagerId = board.managerId,
                creatorId = creator.userId
            )
        }
    }

    private fun checkDuplication(command: ArticleCategoryCreateCommand) {
        // 카테고리 이름 :  역시 그 게시판 내에서 중복되선 안 된다.
        if (articleCategoryPersistencePort.existsByName(command.boardId, command.name)) {
            throw DuplicateArticleCategoryNameException(command.name)
        }
        // 카테고리 슬러그 : 그 게시판 내에서 중복되선 안 된다.
        if (articleCategoryPersistencePort.existsBySlug(command.boardId, command.slug)) {
            throw DuplicateArticleCategorySlugException(command.slug)
        }
    }

    private fun createArticleCategoryAndPersist(command: ArticleCategoryCreateCommand): ArticleCategory {
        val articleCategory = ArticleCategory.create(
            articleCategoryId = articleCategoryIdGenerator.nextId(),
            boardId = command.boardId,
            name = command.name,
            slug = command.slug,
            allowSelfDelete = command.allowSelfDelete,
            allowLike = command.allowLike,
            allowDislike = command.allowDislike,
            createdAt = command.currentTime
        )
        articleCategoryPersistencePort.save(articleCategory)
        return articleCategory
    }
}
