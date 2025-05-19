package com.ttasjwi.board.system.articlelike.domain

import com.ttasjwi.board.system.articlelike.domain.exception.ArticleNotFoundException
import com.ttasjwi.board.system.articlelike.domain.port.ArticleLikeCountPersistencePort
import com.ttasjwi.board.system.articlelike.domain.port.ArticlePersistencePort
import com.ttasjwi.board.system.common.annotation.component.UseCase

@UseCase
class ArticleLikeCountReadUseCaseImpl(
    private val articlePersistencePort: ArticlePersistencePort,
    private val articleLikeCountPersistencePort: ArticleLikeCountPersistencePort,
) : ArticleLikeCountReadUseCase {

    override fun readLikeCount(articleId: Long): ArticleLikeCountReadResponse {
        checkArticleExists(articleId)

        val articleLikeCount = articleLikeCountPersistencePort.findByIdOrNull(articleId)

        return ArticleLikeCountReadResponse(
            articleId = articleId.toString(),
            likeCount = articleLikeCount?.likeCount ?: 0L
        )
    }

    private fun checkArticleExists(articleId: Long) {
        if (!articlePersistencePort.existsByArticleId(articleId)) {
            throw ArticleNotFoundException(articleId = articleId)
        }
    }
}
