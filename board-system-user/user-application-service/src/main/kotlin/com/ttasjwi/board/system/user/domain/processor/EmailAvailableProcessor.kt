package com.ttasjwi.board.system.user.domain.processor

import com.ttasjwi.board.system.common.annotation.component.ApplicationProcessor
import com.ttasjwi.board.system.common.logger.getLogger
import com.ttasjwi.board.system.common.message.MessageResolver
import com.ttasjwi.board.system.user.domain.EmailAvailableResponse
import com.ttasjwi.board.system.user.domain.dto.EmailAvailableQuery
import com.ttasjwi.board.system.user.domain.port.EmailFormatValidatePort
import com.ttasjwi.board.system.user.domain.port.MemberPersistencePort
import java.util.*

@ApplicationProcessor
internal class EmailAvailableProcessor(
    private val emailFormatValidatePort: EmailFormatValidatePort,
    private val memberPersistencePort: MemberPersistencePort,
    private val messageResolver: MessageResolver,
) {

    companion object {
        private val log = getLogger(EmailAvailableProcessor::class.java)
    }

    fun checkEmailAvailable(query: EmailAvailableQuery): EmailAvailableResponse {
        val email = emailFormatValidatePort.validate(query.email)
            .getOrElse {
                log.info { "이메일의 포맷이 유효하지 않습니다. (email = ${query.email})" }
                return makeResponse(query.email, false, "EmailAvailableCheck.InvalidFormat", query.locale)
            }

        if (memberPersistencePort.existsByEmail(email)) {
            log.info { "이미 사용 중인 이메일입니다. (email = ${email})" }
            return makeResponse(email, false, "EmailAvailableCheck.Taken", query.locale)
        }

        log.info { "사용 가능한 이메일입니다. (email = ${email})" }
        return makeResponse(email, true, "EmailAvailableCheck.Available", query.locale)
    }

    private fun makeResponse(
        email: String,
        isAvailable: Boolean,
        reasonCode: String,
        locale: Locale
    ): EmailAvailableResponse {
        return EmailAvailableResponse(
            yourEmail = email,
            isAvailable = isAvailable,
            reasonCode = reasonCode,
            reasonMessage = messageResolver.resolve("$reasonCode.message", locale),
            reasonDescription = messageResolver.resolve("$reasonCode.description", locale)
        )
    }
}
