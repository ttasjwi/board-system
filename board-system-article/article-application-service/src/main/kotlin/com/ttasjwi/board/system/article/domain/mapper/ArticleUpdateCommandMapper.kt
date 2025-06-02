package com.ttasjwi.board.system.article.domain.mapper

import com.ttasjwi.board.system.article.domain.ArticleUpdateRequest
import com.ttasjwi.board.system.article.domain.dto.ArticleUpdateCommand
import com.ttasjwi.board.system.article.domain.policy.ArticleContentPolicy
import com.ttasjwi.board.system.article.domain.policy.ArticleTitlePolicy
import com.ttasjwi.board.system.common.annotation.component.ApplicationCommandMapper
import com.ttasjwi.board.system.common.auth.AuthUserLoader
import com.ttasjwi.board.system.common.exception.NullArgumentException
import com.ttasjwi.board.system.common.exception.ValidationExceptionCollector
import com.ttasjwi.board.system.common.time.TimeManager

@ApplicationCommandMapper
internal class ArticleUpdateCommandMapper(
    private val articleTitlePolicy: ArticleTitlePolicy,
    private val articleContentPolicy: ArticleContentPolicy,
    private val authUserLoader: AuthUserLoader,
    private val timeManager: TimeManager,
) {

    fun mapToCommand(articleId: Long, request: ArticleUpdateRequest): ArticleUpdateCommand {
        val exceptionCollector = ValidationExceptionCollector()

        val title = getTitle(request.title, exceptionCollector)
        val content = getContent(request.content, exceptionCollector)

        exceptionCollector.throwIfNotEmpty()

        return ArticleUpdateCommand(
            articleId = articleId,
            title = title!!,
            content = content!!,
            authUser = authUserLoader.loadCurrentAuthUser()!!,
            currentTime = timeManager.now(),
        )
    }

    private fun getTitle(title: String?, exceptionCollector: ValidationExceptionCollector): String? {
        if (title == null) {
            exceptionCollector.addCustomExceptionOrThrow(NullArgumentException("title"))
            return null
        }
        return articleTitlePolicy.validate(title)
            .getOrElse {
                exceptionCollector.addCustomExceptionOrThrow(it)
                return null
            }
    }

    private fun getContent(content: String?, exceptionCollector: ValidationExceptionCollector): String? {
        if (content == null) {
            exceptionCollector.addCustomExceptionOrThrow(NullArgumentException("content"))
            return null
        }
        return articleContentPolicy.validate(content)
            .getOrElse {
                exceptionCollector.addCustomExceptionOrThrow(it)
                return null
            }
    }
}
