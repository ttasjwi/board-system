package com.ttasjwi.board.system.member.application.mapper

import com.ttasjwi.board.system.core.annotation.component.ApplicationCommandMapper
import com.ttasjwi.board.system.core.exception.NullArgumentException
import com.ttasjwi.board.system.core.exception.ValidationExceptionCollector
import com.ttasjwi.board.system.core.time.TimeManager
import com.ttasjwi.board.system.logging.getLogger
import com.ttasjwi.board.system.member.application.dto.EmailVerificationCommand
import com.ttasjwi.board.system.member.application.mapper.EmailAvailableQueryMapper.Companion
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationRequest
import com.ttasjwi.board.system.member.domain.model.Email
import com.ttasjwi.board.system.member.domain.service.EmailCreator

@ApplicationCommandMapper
internal class EmailVerificationCommandMapper(
    private val emailCreator: EmailCreator,
    private val timeManager: TimeManager,
) {

    companion object {
        private val log = getLogger(EmailVerificationCommandMapper::class.java)
    }

    fun mapToCommand(request: EmailVerificationRequest): EmailVerificationCommand {
        log.info { "요청 입력값이 유효한지 확인합니다." }

        val exceptionCollector = ValidationExceptionCollector()

        val email = getEmail(request.email, exceptionCollector)
        val code = getCode(request.code, exceptionCollector)

        exceptionCollector.throwIfNotEmpty()

        log.info{ "요청 입력값이 유효합니다. (email=$email)"}

        return EmailVerificationCommand(
            email = email!!,
            code = code!!,
            currentTime = timeManager.now()
        )
    }

    private fun getEmail(email: String?, exceptionCollector: ValidationExceptionCollector): Email? {
        if (email == null) {
            log.warn { "이메일이 누락됐습니다." }
            exceptionCollector.addCustomExceptionOrThrow(NullArgumentException("email"))
            return null
        }
        return emailCreator.create(email)
            .getOrElse {
                log.warn { "이메일 포맷이 유효하지 않습니다. (email=${email})" }
                exceptionCollector.addCustomExceptionOrThrow(it)
                return null
            }
    }

    private fun getCode(code: String?, exceptionCollector: ValidationExceptionCollector): String? {
        if (code == null) {
            log.warn { "이메일 인증 코드가 누락됐습니다." }
            exceptionCollector.addCustomExceptionOrThrow(NullArgumentException("code"))
            return null
        }
        return code
    }
}
