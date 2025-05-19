package com.ttasjwi.board.system.articlelike.domain.mapper

import com.ttasjwi.board.system.articlelike.domain.ArticleDislikeCreateRequest
import com.ttasjwi.board.system.articlelike.domain.dto.ArticleDislikeCreateCommand
import com.ttasjwi.board.system.common.annotation.component.ApplicationCommandMapper
import com.ttasjwi.board.system.common.auth.AuthUserLoader
import com.ttasjwi.board.system.common.exception.NullArgumentException
import com.ttasjwi.board.system.common.exception.ValidationExceptionCollector
import com.ttasjwi.board.system.common.time.TimeManager

@ApplicationCommandMapper
internal class ArticleDislikeCreateCommandMapper(
    private val authUserLoader: AuthUserLoader,
    private val timeManager: TimeManager,
) {

    fun mapToCommand(request: ArticleDislikeCreateRequest): ArticleDislikeCreateCommand {
        val exceptionCollector = ValidationExceptionCollector()

        val articleId = getArticleId(request.articleId, exceptionCollector)

        exceptionCollector.throwIfNotEmpty()

        return ArticleDislikeCreateCommand(
            articleId = articleId!!,
            dislikeUser = authUserLoader.loadCurrentAuthUser()!!,
            currentTime = timeManager.now()
        )
    }

    private fun getArticleId(articleId: Long?, exceptionCollector: ValidationExceptionCollector): Long? {
        if (articleId == null) {
            exceptionCollector.addCustomExceptionOrThrow(NullArgumentException("articleId"))
            return null
        }
        return articleId
    }
}
