package com.ttasjwi.board.system.board.domain

import com.ttasjwi.board.system.board.domain.mapper.ArticleCategoryCreateCommandMapper
import com.ttasjwi.board.system.board.domain.model.ArticleCategory
import com.ttasjwi.board.system.board.domain.processor.ArticleCategoryCreateProcessor
import com.ttasjwi.board.system.common.annotation.component.UseCase

@UseCase
internal class ArticleCategoryCreateUseCaseImpl(
    private val commandMapper: ArticleCategoryCreateCommandMapper,
    private val processor: ArticleCategoryCreateProcessor,
) : ArticleCategoryCreateUseCase {

    override fun create(boardId: Long, request: ArticleCategoryCreateRequest): ArticleCategoryCreateResponse {
        val command = commandMapper.mapToCommand(boardId, request)
        val articleCategory = processor.create(command)
        return makeResponse(articleCategory)
    }

    private fun makeResponse(articleCategory: ArticleCategory): ArticleCategoryCreateResponse {
        return ArticleCategoryCreateResponse(
            articleCategoryId = articleCategory.articleCategoryId.toString(),
            boardId = articleCategory.boardId.toString(),
            name = articleCategory.name,
            slug = articleCategory.slug,
            allowSelfEditDelete = articleCategory.allowSelfEditDelete,
            allowLike = articleCategory.allowLike,
            allowDislike = articleCategory.allowDislike,
            createdAt = articleCategory.createdAt.toZonedDateTime(),
        )
    }
}
