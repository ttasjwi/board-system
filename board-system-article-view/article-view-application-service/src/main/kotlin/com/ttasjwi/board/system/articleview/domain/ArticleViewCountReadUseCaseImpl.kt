package com.ttasjwi.board.system.articleview.domain

import com.ttasjwi.board.system.articleview.domain.exception.ArticleNotFoundException
import com.ttasjwi.board.system.articleview.domain.port.ArticlePersistencePort
import com.ttasjwi.board.system.articleview.domain.port.ArticleViewCountPersistencePort
import com.ttasjwi.board.system.common.annotation.component.UseCase

@UseCase
internal class ArticleViewCountReadUseCaseImpl(
    private val articlePersistencePort: ArticlePersistencePort,
    private val articleViewCountPersistencePort: ArticleViewCountPersistencePort
) : ArticleViewCountReadUseCase {

    override fun readViewCount(articleId: Long): ArticleViewCountReadResponse {
        checkArticleExistence(articleId)
        val articleViewCount = articleViewCountPersistencePort.findByIdOrNull(articleId)

        return ArticleViewCountReadResponse(
            articleId = articleId.toString(),
            viewCount = articleViewCount?.viewCount ?: 0L,
        )
    }

    private fun checkArticleExistence(articleId: Long) {
        if (!articlePersistencePort.existsById(articleId)) {
            throw ArticleNotFoundException(articleId)
        }
    }
}
