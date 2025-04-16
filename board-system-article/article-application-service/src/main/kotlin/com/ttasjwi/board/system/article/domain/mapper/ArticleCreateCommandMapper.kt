package com.ttasjwi.board.system.article.domain.mapper

import com.ttasjwi.board.system.article.domain.ArticleCreateRequest
import com.ttasjwi.board.system.article.domain.dto.ArticleCreateCommand
import com.ttasjwi.board.system.article.domain.policy.ArticleContentPolicy
import com.ttasjwi.board.system.article.domain.policy.ArticleTitlePolicy
import com.ttasjwi.board.system.common.annotation.component.ApplicationCommandMapper
import com.ttasjwi.board.system.common.auth.AuthMemberLoader
import com.ttasjwi.board.system.common.exception.NullArgumentException
import com.ttasjwi.board.system.common.exception.ValidationExceptionCollector
import com.ttasjwi.board.system.common.time.TimeManager

@ApplicationCommandMapper
internal class ArticleCreateCommandMapper(
    private val articleTitlePolicy: ArticleTitlePolicy,
    private val articleContentPolicy: ArticleContentPolicy,
    private val authMemberLoader: AuthMemberLoader,
    private val timeManager: TimeManager,
) {

    fun mapToCommand(request: ArticleCreateRequest): ArticleCreateCommand {
        val exceptionCollector = ValidationExceptionCollector()

        val title = getTitle(request.title, exceptionCollector)
        val content = getContent(request.content, exceptionCollector)
        val articleCategoryId = getArticleCategoryId(request.articleCategoryId, exceptionCollector)

        exceptionCollector.throwIfNotEmpty()

        return ArticleCreateCommand(
            title = title!!,
            content = content!!,
            articleCategoryId = articleCategoryId!!,
            writer = authMemberLoader.loadCurrentAuthMember()!!,
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

    private fun getArticleCategoryId(
        articleCategoryId: Long?,
        exceptionCollector: ValidationExceptionCollector
    ): Long? {
        if (articleCategoryId == null) {
            exceptionCollector.addCustomExceptionOrThrow(NullArgumentException("articleCategoryId"))
            return null
        }
        return articleCategoryId
    }
}
