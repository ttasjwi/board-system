package com.ttasjwi.board.system.member.application.mapper

import com.ttasjwi.board.system.core.annotation.component.ApplicationCommandMapper
import com.ttasjwi.board.system.core.exception.NullArgumentException
import com.ttasjwi.board.system.core.locale.LocaleManager
import com.ttasjwi.board.system.core.time.TimeManager
import com.ttasjwi.board.system.logging.getLogger
import com.ttasjwi.board.system.member.application.dto.EmailVerificationStartCommand
import com.ttasjwi.board.system.member.application.usecase.EmailVerificationStartRequest
import com.ttasjwi.board.system.member.domain.service.EmailCreator

@ApplicationCommandMapper
internal class EmailVerificationStartCommandMapper(
    private val emailCreator: EmailCreator,
    private val localeManager: LocaleManager,
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
        val email = emailCreator.create(request.email).getOrElse {
            log.warn(it)
            throw it
        }
        log.info { "입력값이 유효합니다. (email = ${request.email})" }

        return EmailVerificationStartCommand(
            email = email,
            currenTime = timeManager.now(),
            locale = localeManager.getCurrentLocale(),
        )
    }

}
