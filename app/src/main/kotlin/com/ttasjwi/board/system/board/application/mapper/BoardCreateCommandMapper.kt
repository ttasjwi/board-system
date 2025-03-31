package com.ttasjwi.board.system.board.application.mapper

import com.ttasjwi.board.system.board.application.dto.BoardCreateCommand
import com.ttasjwi.board.system.board.application.usecase.BoardCreateRequest
import com.ttasjwi.board.system.board.domain.model.BoardDescription
import com.ttasjwi.board.system.board.domain.model.BoardName
import com.ttasjwi.board.system.board.domain.model.BoardSlug
import com.ttasjwi.board.system.board.domain.service.BoardDescriptionCreator
import com.ttasjwi.board.system.board.domain.service.BoardNameCreator
import com.ttasjwi.board.system.board.domain.service.BoardSlugCreator
import com.ttasjwi.board.system.common.annotation.component.ApplicationCommandMapper
import com.ttasjwi.board.system.common.auth.domain.service.AuthMemberLoader
import com.ttasjwi.board.system.common.exception.NullArgumentException
import com.ttasjwi.board.system.common.exception.ValidationExceptionCollector
import com.ttasjwi.board.system.common.logging.getLogger
import com.ttasjwi.board.system.common.time.TimeManager

@ApplicationCommandMapper
internal class BoardCreateCommandMapper(
    private val boardNameCreator: BoardNameCreator,
    private val boardDescriptionCreator: BoardDescriptionCreator,
    private val boardSlugCreator: BoardSlugCreator,
    private val authMemberLoader: AuthMemberLoader,
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
            creator = authMemberLoader.loadCurrentAuthMember()!!,
            currentTime = timeManager.now(),
        )
    }

    private fun getBoardName(boardName: String?, exceptionCollector: ValidationExceptionCollector): BoardName? {
        if (boardName == null) {
            val e = NullArgumentException("boardName")
            log.warn(e)
            exceptionCollector.addCustomExceptionOrThrow(e)
            return null
        }
        return boardNameCreator.create(boardName)
            .getOrElse {
                log.warn(it)
                exceptionCollector.addCustomExceptionOrThrow(it)
                return null
            }
    }

    private fun getBoardDescription(
        boardDescription: String?,
        exceptionCollector: ValidationExceptionCollector
    ): BoardDescription? {
        if (boardDescription == null) {
            val e = NullArgumentException("boardDescription")
            log.warn(e)
            exceptionCollector.addCustomExceptionOrThrow(e)
            return null
        }
        return boardDescriptionCreator.create(boardDescription)
            .getOrElse {
                log.warn(it)
                exceptionCollector.addCustomExceptionOrThrow(it)
                return null
            }
    }

    private fun getBoardSlug(boardSlug: String?, exceptionCollector: ValidationExceptionCollector): BoardSlug? {
        if (boardSlug == null) {
            val e = NullArgumentException("boardSlug")
            log.warn(e)
            exceptionCollector.addCustomExceptionOrThrow(e)
            return null
        }
        return boardSlugCreator.create(boardSlug)
            .getOrElse {
                log.warn(it)
                exceptionCollector.addCustomExceptionOrThrow(it)
                return null
            }
    }
}
