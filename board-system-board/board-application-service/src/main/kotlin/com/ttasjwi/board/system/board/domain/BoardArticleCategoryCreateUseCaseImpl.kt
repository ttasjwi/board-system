package com.ttasjwi.board.system.board.domain

import com.ttasjwi.board.system.board.domain.mapper.BoardArticleCategoryCreateCommandMapper
import com.ttasjwi.board.system.board.domain.model.BoardArticleCategory
import com.ttasjwi.board.system.board.domain.processor.BoardArticleCategoryCreateProcessor
import com.ttasjwi.board.system.common.annotation.component.UseCase

@UseCase
internal class BoardArticleCategoryCreateUseCaseImpl(
    private val commandMapper: BoardArticleCategoryCreateCommandMapper,
    private val processor: BoardArticleCategoryCreateProcessor,
) : BoardArticleCategoryCreateUseCase {

    override fun create(boardId: Long, request: BoardArticleCategoryCreateRequest): BoardArticleCategoryCreateResponse {
        val command = commandMapper.mapToCommand(boardId, request)
        val boardArticleCategory = processor.create(command)
        return makeResponse(boardArticleCategory)
    }

    private fun makeResponse(boardArticleCategory: BoardArticleCategory): BoardArticleCategoryCreateResponse {
        return BoardArticleCategoryCreateResponse(
            boardArticleCategoryId = boardArticleCategory.boardArticleCategoryId.toString(),
            boardId = boardArticleCategory.boardId.toString(),
            name = boardArticleCategory.name,
            slug = boardArticleCategory.slug,
            allowSelfDelete = boardArticleCategory.allowSelfDelete,
            allowLike = boardArticleCategory.allowLike,
            allowDislike = boardArticleCategory.allowDislike,
            createdAt = boardArticleCategory.createdAt.toZonedDateTime(),
        )
    }
}
