package com.ttasjwi.board.system.board.domain.mapper

import com.ttasjwi.board.system.board.domain.BoardCreateRequest
import com.ttasjwi.board.system.board.domain.dto.BoardCreateCommand
import com.ttasjwi.board.system.board.domain.policy.BoardDescriptionPolicy
import com.ttasjwi.board.system.board.domain.policy.BoardNamePolicy
import com.ttasjwi.board.system.board.domain.policy.BoardSlugPolicy
import com.ttasjwi.board.system.common.annotation.component.ApplicationCommandMapper
import com.ttasjwi.board.system.common.auth.AuthUserLoader
import com.ttasjwi.board.system.common.exception.NullArgumentException
import com.ttasjwi.board.system.common.exception.ValidationExceptionCollector
import com.ttasjwi.board.system.common.logger.getLogger
import com.ttasjwi.board.system.common.time.TimeManager

@ApplicationCommandMapper
internal class BoardCreateCommandMapper(
    private val boardNamePolicy: BoardNamePolicy,
    private val boardDescriptionPolicy: BoardDescriptionPolicy,
    private val boardSlugPolicy: BoardSlugPolicy,
    private val authUserLoader: AuthUserLoader,
    private val timeManager: TimeManager,
) {

    companion object {
        private val log = getLogger(BoardCreateCommandMapper::class.java)
    }

    fun mapToCommand(request: BoardCreateRequest): BoardCreateCommand {
        log.info { "요청 입력값이 유효한 지 확인합니다." }
        val exceptionCollector = ValidationExceptionCollector()

        val boardName = getBoardName(request.name, exceptionCollector)
        val boardDescription = getBoardDescription(request.description, exceptionCollector)
        val boardSlug = getBoardSlug(request.slug, exceptionCollector)

        exceptionCollector.throwIfNotEmpty()

        log.info { "요청 입력값들은 유효합니다." }

        return BoardCreateCommand(
            boardName = boardName!!,
            boardDescription = boardDescription!!,
            boardSlug = boardSlug!!,
            creator = authUserLoader.loadCurrentAuthUser()!!,
            currentTime = timeManager.now(),
        )
    }

    private fun getBoardName(boardName: String?, exceptionCollector: ValidationExceptionCollector): String? {
        if (boardName == null) {
            val e = NullArgumentException("boardName")
            log.warn(e)
            exceptionCollector.addCustomExceptionOrThrow(e)
            return null
        }
        return boardNamePolicy.validate(boardName)
            .getOrElse {
                log.warn(it)
                exceptionCollector.addCustomExceptionOrThrow(it)
                return null
            }
    }

    private fun getBoardDescription(
        boardDescription: String?,
        exceptionCollector: ValidationExceptionCollector
    ): String? {
        if (boardDescription == null) {
            val e = NullArgumentException("boardDescription")
            log.warn(e)
            exceptionCollector.addCustomExceptionOrThrow(e)
            return null
        }
        return boardDescriptionPolicy.create(boardDescription)
            .getOrElse {
                log.warn(it)
                exceptionCollector.addCustomExceptionOrThrow(it)
                return null
            }
    }

    private fun getBoardSlug(boardSlug: String?, exceptionCollector: ValidationExceptionCollector): String? {
        if (boardSlug == null) {
            val e = NullArgumentException("boardSlug")
            log.warn(e)
            exceptionCollector.addCustomExceptionOrThrow(e)
            return null
        }
        return boardSlugPolicy.validate(boardSlug)
            .getOrElse {
                log.warn(it)
                exceptionCollector.addCustomExceptionOrThrow(it)
                return null
            }
    }
}
