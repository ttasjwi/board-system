package com.ttasjwi.board.system.board.domain.mapper

import com.ttasjwi.board.system.board.domain.ArticleCategoryCreateRequest
import com.ttasjwi.board.system.board.domain.dto.ArticleCategoryCreateCommand
import com.ttasjwi.board.system.board.domain.policy.ArticleCategoryNamePolicy
import com.ttasjwi.board.system.board.domain.policy.ArticleCategorySlugPolicy
import com.ttasjwi.board.system.common.annotation.component.ApplicationCommandMapper
import com.ttasjwi.board.system.common.auth.AuthUserLoader
import com.ttasjwi.board.system.common.exception.NullArgumentException
import com.ttasjwi.board.system.common.exception.ValidationExceptionCollector
import com.ttasjwi.board.system.common.time.TimeManager

@ApplicationCommandMapper
internal class ArticleCategoryCreateCommandMapper(
    private val articleCategoryNamePolicy: ArticleCategoryNamePolicy,
    private val articleCategorySlugPolicy: ArticleCategorySlugPolicy,
    private val authUserLoader: AuthUserLoader,
    private val timeManager: TimeManager
) {

    fun mapToCommand(boardId: Long, request: ArticleCategoryCreateRequest): ArticleCategoryCreateCommand {
        val exceptionCollector = ValidationExceptionCollector()

        val articleCategoryName = getArticleCategoryName(request.name, exceptionCollector)
        val articleCategorySlug = getArticleCategorySlug(request.slug, exceptionCollector)
        val allowWrite = getAllowWrite(request.allowWrite, exceptionCollector)
        val allowSelfEditDelete = getAllowSelfEditDelete(request.allowSelfEditDelete, exceptionCollector)
        val allowComment = getAllowComment(request.allowComment, exceptionCollector)
        val allowLike = getAllowLike(request.allowLike, exceptionCollector)
        val allowDislike = getAllowDislike(request.allowDislike, exceptionCollector)

        exceptionCollector.throwIfNotEmpty()

        return ArticleCategoryCreateCommand(
            boardId = boardId,
            creator = authUserLoader.loadCurrentAuthUser()!!,
            name = articleCategoryName!!,
            slug = articleCategorySlug!!,
            allowWrite = allowWrite!!,
            allowSelfEditDelete = allowSelfEditDelete!!,
            allowComment = allowComment!!,
            allowLike = allowLike!!,
            allowDislike = allowDislike!!,
            currentTime = timeManager.now()
        )
    }

    private fun getArticleCategoryName(
        articleCategoryName: String?,
        exceptionCollector: ValidationExceptionCollector
    ): String? {
        if (articleCategoryName == null) {
            exceptionCollector.addCustomExceptionOrThrow(
                NullArgumentException("name")
            )
            return null
        }
        return articleCategoryNamePolicy.validate(articleCategoryName)
            .getOrElse {
                exceptionCollector.addCustomExceptionOrThrow(it)
                return null
            }
    }

    private fun getArticleCategorySlug(
        articleCategorySlug: String?,
        exceptionCollector: ValidationExceptionCollector
    ): String? {
        if (articleCategorySlug == null) {
            exceptionCollector.addCustomExceptionOrThrow(
                NullArgumentException("slug")
            )
            return null
        }
        return articleCategorySlugPolicy.validate(articleCategorySlug)
            .getOrElse {
                exceptionCollector.addCustomExceptionOrThrow(it)
                return null
            }
    }

    private fun getAllowWrite(
        allowWrite: Boolean?,
        exceptionCollector: ValidationExceptionCollector
    ): Boolean? {
        if (allowWrite == null) {
            exceptionCollector.addCustomExceptionOrThrow(
                NullArgumentException("allowWrite")
            )
            return null
        }
        return allowWrite
    }

    private fun getAllowSelfEditDelete(
        allowSelfEditDelete: Boolean?,
        exceptionCollector: ValidationExceptionCollector
    ): Boolean? {
        if (allowSelfEditDelete == null) {
            exceptionCollector.addCustomExceptionOrThrow(
                NullArgumentException("allowSelfEditDelete")
            )
            return null
        }
        return allowSelfEditDelete
    }

    private fun getAllowComment(
        allowComment: Boolean?,
        exceptionCollector: ValidationExceptionCollector
    ): Boolean? {
        if (allowComment == null) {
            exceptionCollector.addCustomExceptionOrThrow(
                NullArgumentException("allowComment")
            )
            return null
        }
        return allowComment
    }

    private fun getAllowLike(allowLike: Boolean?, exceptionCollector: ValidationExceptionCollector): Boolean? {
        if (allowLike == null) {
            exceptionCollector.addCustomExceptionOrThrow(
                NullArgumentException("allowLike")
            )
            return null
        }
        return allowLike
    }

    private fun getAllowDislike(allowDislike: Boolean?, exceptionCollector: ValidationExceptionCollector): Boolean? {
        if (allowDislike == null) {
            exceptionCollector.addCustomExceptionOrThrow(
                NullArgumentException("allowDislike")
            )
            return null
        }
        return allowDislike
    }
}
