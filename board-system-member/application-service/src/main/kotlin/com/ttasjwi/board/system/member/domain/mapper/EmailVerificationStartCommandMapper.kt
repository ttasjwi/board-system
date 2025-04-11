package com.ttasjwi.board.system.member.domain.mapper

import com.ttasjwi.board.system.common.annotation.component.ApplicationCommandMapper
import com.ttasjwi.board.system.common.exception.NullArgumentException
import com.ttasjwi.board.system.common.locale.LocaleResolver
import com.ttasjwi.board.system.common.logger.getLogger
import com.ttasjwi.board.system.common.time.TimeManager
import com.ttasjwi.board.system.member.domain.EmailVerificationStartRequest
import com.ttasjwi.board.system.member.domain.dto.EmailVerificationStartCommand
import com.ttasjwi.board.system.member.domain.port.EmailFormatValidatePort

@ApplicationCommandMapper
internal class EmailVerificationStartCommandMapper(
    private val emailFormatValidatePort: EmailFormatValidatePort,
    private val localeResolver: LocaleResolver,
    private val timeManager: TimeManager,
) {

    companion object {
        private val log = getLogger(EmailVerificationStartCommandMapper::class.java)
    }

    fun mapToCommand(request: EmailVerificationStartRequest): EmailVerificationStartCommand {
        log.info { "요청이 유효한지 확인합니다." }

        if (request.email == null) {
            val e = NullArgumentException("email")
            log.warn(e)
            throw e
        }
        val email = emailFormatValidatePort.validate(request.email!!).getOrElse {
            log.warn(it)
            throw it
        }
        log.info { "입력값이 유효합니다. (email = ${request.email})" }

        return EmailVerificationStartCommand(
            email = email,
            currenTime = timeManager.now(),
            locale = localeResolver.getCurrentLocale(),
        )
    }
}
