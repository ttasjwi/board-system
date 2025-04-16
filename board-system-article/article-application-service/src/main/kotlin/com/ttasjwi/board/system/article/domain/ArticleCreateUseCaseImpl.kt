package com.ttasjwi.board.system.article.domain

import com.ttasjwi.board.system.article.domain.mapper.ArticleCreateCommandMapper
import com.ttasjwi.board.system.article.domain.model.Article
import com.ttasjwi.board.system.article.domain.processor.ArticleCreateProcessor
import com.ttasjwi.board.system.common.annotation.component.UseCase

@UseCase
internal class ArticleCreateUseCaseImpl(
    private val commandMapper: ArticleCreateCommandMapper,
    private val processor: ArticleCreateProcessor,
) : ArticleCreateUseCase {

    override fun create(request: ArticleCreateRequest): ArticleCreateResponse {
        val command = commandMapper.mapToCommand(request)
        val article = processor.create(command)

        return makeResponse(article)
    }

    private fun makeResponse(article: Article): ArticleCreateResponse {
        return ArticleCreateResponse(
            articleId = article.articleId.toString(),
            title = article.title,
            content = article.content,
            boardId = article.boardId.toString(),
            articleCategoryId = article.articleCategoryId.toString(),
            writerId = article.writerId.toString(),
            createdAt = article.createdAt.toZonedDateTime(),
            modifiedAt = article.modifiedAt.toZonedDateTime(),
        )
    }
}
