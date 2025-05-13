package com.ttasjwi.board.system.articlecomment.domain.mapper

import com.ttasjwi.board.system.articlecomment.domain.ArticleCommentCreateRequest
import com.ttasjwi.board.system.articlecomment.domain.dto.ArticleCommentCreateCommand
import com.ttasjwi.board.system.articlecomment.domain.policy.ArticleCommentContentPolicy
import com.ttasjwi.board.system.common.annotation.component.ApplicationCommandMapper
import com.ttasjwi.board.system.common.auth.AuthUserLoader
import com.ttasjwi.board.system.common.exception.NullArgumentException
import com.ttasjwi.board.system.common.exception.ValidationExceptionCollector
import com.ttasjwi.board.system.common.time.TimeManager

@ApplicationCommandMapper
internal class ArticleCommentCreateCommandMapper(
    private val articleCommentContentPolicy: ArticleCommentContentPolicy,
    private val timeManager: TimeManager,
    private val authUserLoader: AuthUserLoader,
) {

    fun mapToCommand(request: ArticleCommentCreateRequest): ArticleCommentCreateCommand {
        val exceptionCollector = ValidationExceptionCollector()
        val content = getContent(request.content, exceptionCollector)
        val articleId = getArticleId(request.articleId, exceptionCollector)
        exceptionCollector.throwIfNotEmpty()
        return ArticleCommentCreateCommand(
            content = content!!,
            articleId = articleId!!,
            parentCommentId = request.parentCommentId,
            currentTime = timeManager.now(),
            commentWriter = authUserLoader.loadCurrentAuthUser()!!,
        )
    }

    private fun getContent(content: String?, exceptionCollector: ValidationExceptionCollector): String? {
        if (content == null) {
            exceptionCollector.addCustomExceptionOrThrow(NullArgumentException("content"))
            return null
        }
        return articleCommentContentPolicy
            .validate(content)
            .getOrElse {
                exceptionCollector.addCustomExceptionOrThrow(it)
                return null
            }
    }

    private fun getArticleId(articleId: Long?, exceptionCollector: ValidationExceptionCollector): Long? {
        if (articleId == null) {
            exceptionCollector.addCustomExceptionOrThrow(NullArgumentException("articleId"))
            return null
        }
        return articleId
    }

}
