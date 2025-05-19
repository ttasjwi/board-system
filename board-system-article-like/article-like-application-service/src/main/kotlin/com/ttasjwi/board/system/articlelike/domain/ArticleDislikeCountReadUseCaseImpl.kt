package com.ttasjwi.board.system.articlelike.domain

import com.ttasjwi.board.system.articlelike.domain.exception.ArticleNotFoundException
import com.ttasjwi.board.system.articlelike.domain.port.ArticleDislikeCountPersistencePort
import com.ttasjwi.board.system.articlelike.domain.port.ArticlePersistencePort
import com.ttasjwi.board.system.common.annotation.component.UseCase

@UseCase
class ArticleDislikeCountReadUseCaseImpl(
    private val articlePersistencePort: ArticlePersistencePort,
    private val articleDislikeCountPersistencePort: ArticleDislikeCountPersistencePort,
) : ArticleDislikeCountReadUseCase {

    override fun readDislikeCount(articleId: Long): ArticleDislikeCountReadResponse {
        checkArticleExists(articleId)

        val articleDislikeCount = articleDislikeCountPersistencePort.findByIdOrNull(articleId)

        return ArticleDislikeCountReadResponse(
            articleId = articleId.toString(),
            dislikeCount = articleDislikeCount?.dislikeCount ?: 0L
        )
    }

    private fun checkArticleExists(articleId: Long) {
        if (!articlePersistencePort.existsByArticleId(articleId)) {
            throw ArticleNotFoundException(articleId = articleId)
        }
    }
}
