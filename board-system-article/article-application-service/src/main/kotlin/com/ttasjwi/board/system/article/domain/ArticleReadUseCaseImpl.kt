package com.ttasjwi.board.system.article.domain

import com.ttasjwi.board.system.article.domain.exception.ArticleNotFoundException
import com.ttasjwi.board.system.article.domain.model.Article
import com.ttasjwi.board.system.article.domain.port.ArticlePersistencePort
import com.ttasjwi.board.system.common.annotation.component.UseCase

@UseCase
internal class ArticleReadUseCaseImpl(
    private val articlePersistencePort: ArticlePersistencePort
) : ArticleReadUseCase {

    override fun read(articleId: Long): ArticleReadResponse {
        val article = getArticleOrThrow(articleId)
        return makeResponse(article)
    }

    private fun getArticleOrThrow(articleId: Long): Article {
        return articlePersistencePort.findByIdOrNull(articleId)
            ?: throw ArticleNotFoundException(
                source = "articleId",
                argument = articleId
            )
    }

    private fun makeResponse(article: Article): ArticleReadResponse {
        return ArticleReadResponse(
            articleId = article.articleId.toString(),
            title = article.title,
            content = article.content,
            boardId = article.boardId.toString(),
            articleCategoryId = article.articleCategoryId.toString(),
            writerId = article.writerId.toString(),
            writerNickname = article.writerNickname,
            createdAt = article.createdAt.toZonedDateTime(),
            modifiedAt = article.modifiedAt.toZonedDateTime(),
        )
    }
}
