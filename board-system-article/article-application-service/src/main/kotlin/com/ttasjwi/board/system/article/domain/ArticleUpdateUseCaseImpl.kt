package com.ttasjwi.board.system.article.domain

import com.ttasjwi.board.system.article.domain.mapper.ArticleUpdateCommandMapper
import com.ttasjwi.board.system.article.domain.model.Article
import com.ttasjwi.board.system.article.domain.processor.ArticleUpdateProcessor
import com.ttasjwi.board.system.common.annotation.component.UseCase

@UseCase
internal class ArticleUpdateUseCaseImpl(
    private val articleUpdateCommandMapper: ArticleUpdateCommandMapper,
    private val articleUpdateProcessor: ArticleUpdateProcessor,
) : ArticleUpdateUseCase {

    override fun update(articleId: Long, request: ArticleUpdateRequest): ArticleUpdateResponse {
        val command = articleUpdateCommandMapper.mapToCommand(articleId, request)
        val article = articleUpdateProcessor.update(command)
        return makeResponse(article)
    }

    private fun makeResponse(article: Article): ArticleUpdateResponse {
        return ArticleUpdateResponse(
            articleId = article.articleId.toString(),
            title = article.title,
            content= article.content,
            boardId = article.boardId.toString(),
            articleCategoryId = article.articleCategoryId.toString(),
            writerId = article.writerId.toString(),
            writerNickname = article.writerNickname,
            createdAt = article.createdAt.toZonedDateTime(),
            modifiedAt = article.modifiedAt.toZonedDateTime(),
        )
    }
}
