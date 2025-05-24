package com.ttasjwi.board.system.articlecomment.domain

import com.ttasjwi.board.system.articlecomment.domain.exception.ArticleNotFoundException
import com.ttasjwi.board.system.articlecomment.domain.port.ArticleCommentCountPersistencePort
import com.ttasjwi.board.system.articlecomment.domain.port.ArticlePersistencePort
import com.ttasjwi.board.system.common.annotation.component.UseCase

@UseCase
internal class ArticleCommentCountReadUseCaseImpl(
    private val articlePersistencePort: ArticlePersistencePort,
    private val articleCommentCountPersistencePort: ArticleCommentCountPersistencePort
) : ArticleCommentCountReadUseCase {

    override fun readCommentCount(articleId: Long): ArticleCommentCountReadResponse {
        checkArticleExistence(articleId)
        val articleCommentCount = articleCommentCountPersistencePort.findByIdOrNull(articleId)

        return ArticleCommentCountReadResponse(
            articleId = articleId.toString(),
            commentCount = articleCommentCount?.commentCount ?: 0L,
        )
    }

    private fun checkArticleExistence(articleId: Long) {
        if (!articlePersistencePort.existsById(articleId)) {
            throw ArticleNotFoundException(articleId)
        }
    }
}
