package com.ttasjwi.board.system.board.domain.mapper

import com.ttasjwi.board.system.board.domain.BoardArticleCategoryCreateRequest
import com.ttasjwi.board.system.board.domain.dto.BoardArticleCategoryCreateCommand
import com.ttasjwi.board.system.board.domain.policy.BoardArticleCategoryNamePolicy
import com.ttasjwi.board.system.board.domain.policy.BoardArticleCategorySlugPolicy
import com.ttasjwi.board.system.common.annotation.component.ApplicationCommandMapper
import com.ttasjwi.board.system.common.auth.AuthMemberLoader
import com.ttasjwi.board.system.common.exception.NullArgumentException
import com.ttasjwi.board.system.common.exception.ValidationExceptionCollector
import com.ttasjwi.board.system.common.time.TimeManager

@ApplicationCommandMapper
internal class BoardArticleCategoryCreateCommandMapper(
    private val boardArticleCategoryNamePolicy: BoardArticleCategoryNamePolicy,
    private val boardArticleCategorySlugPolicy: BoardArticleCategorySlugPolicy,
    private val authMemberLoader: AuthMemberLoader,
    private val timeManager: TimeManager
) {

    fun mapToCommand(boardId: Long, request: BoardArticleCategoryCreateRequest): BoardArticleCategoryCreateCommand {
        val exceptionCollector = ValidationExceptionCollector()

        val boardArticleCategoryName = getBoardArticleCategoryName(request.name, exceptionCollector)
        val boardArticleCategorySlug = getBoardArticleCategorySlug(request.slug, exceptionCollector)
        val allowSelfDelete = getAllowSelfDelete(request.allowSelfDelete, exceptionCollector)
        val allowLike = getAllowLike(request.allowLike, exceptionCollector)
        val allowDislike = getAllowDislike(request.allowDislike, exceptionCollector)

        exceptionCollector.throwIfNotEmpty()

        return BoardArticleCategoryCreateCommand(
            boardId = boardId,
            creator = authMemberLoader.loadCurrentAuthMember()!!,
            boardArticleCategoryName = boardArticleCategoryName!!,
            boardArticleCategorySlug = boardArticleCategorySlug!!,
            allowSelfDelete = allowSelfDelete!!,
            allowLike = allowLike!!,
            allowDislike = allowDislike!!,
            currentTime = timeManager.now()
        )
    }

    private fun getBoardArticleCategoryName(
        boardArticleCategoryName: String?,
        exceptionCollector: ValidationExceptionCollector
    ): String? {
        if (boardArticleCategoryName == null) {
            exceptionCollector.addCustomExceptionOrThrow(
                NullArgumentException("name")
            )
            return null
        }
        return boardArticleCategoryNamePolicy.validate(boardArticleCategoryName)
            .getOrElse {
                exceptionCollector.addCustomExceptionOrThrow(it)
                return null
            }
    }

    private fun getBoardArticleCategorySlug(
        boardArticleCategorySlug: String?,
        exceptionCollector: ValidationExceptionCollector
    ): String? {
        if (boardArticleCategorySlug == null) {
            exceptionCollector.addCustomExceptionOrThrow(
                NullArgumentException("slug")
            )
            return null
        }
        return boardArticleCategorySlugPolicy.validate(boardArticleCategorySlug)
            .getOrElse {
                exceptionCollector.addCustomExceptionOrThrow(it)
                return null
            }
    }

    private fun getAllowSelfDelete(
        allowSelfDelete: Boolean?,
        exceptionCollector: ValidationExceptionCollector
    ): Boolean? {
        if (allowSelfDelete == null) {
            exceptionCollector.addCustomExceptionOrThrow(
                NullArgumentException("allowSelfDelete")
            )
            return null
        }
        return allowSelfDelete
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
