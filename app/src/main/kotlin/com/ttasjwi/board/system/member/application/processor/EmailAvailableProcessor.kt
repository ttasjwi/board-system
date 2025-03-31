package com.ttasjwi.board.system.member.application.processor

import com.ttasjwi.board.system.common.annotation.component.ApplicationProcessor
import com.ttasjwi.board.system.common.locale.LocaleManager
import com.ttasjwi.board.system.common.logging.getLogger
import com.ttasjwi.board.system.common.message.MessageResolver
import com.ttasjwi.board.system.member.application.dto.EmailAvailableQuery
import com.ttasjwi.board.system.member.application.usecase.EmailAvailableResponse
import com.ttasjwi.board.system.member.domain.service.EmailCreator
import com.ttasjwi.board.system.member.domain.service.MemberFinder

@ApplicationProcessor
internal class EmailAvailableProcessor(
    private val emailCreator: EmailCreator,
    private val memberFinder: MemberFinder,
    private val messageResolver: MessageResolver,
    private val localeManager: LocaleManager,
) {

    companion object {
        private val log = getLogger(EmailAvailableProcessor::class.java)
    }

    fun checkEmailAvailable(query: EmailAvailableQuery): EmailAvailableResponse {
        val email = emailCreator.create(query.email)
            .getOrElse {
                log.info { "이메일의 포맷이 유효하지 않습니다. (email = ${query.email})" }
                return makeResponse(query.email, false, "EmailAvailableCheck.InvalidFormat")
            }

        if (memberFinder.existsByEmail(email)) {
            log.info { "이미 사용 중인 이메일입니다. (email = ${email.value})" }
            return makeResponse(query.email, false, "EmailAvailableCheck.Taken")
        }

        log.info { "사용 가능한 이메일입니다. (email = ${email.value})" }
        return makeResponse(query.email, true, "EmailAvailableCheck.Available")
    }

    private fun makeResponse(email: String, isAvailable: Boolean, reasonCode: String): EmailAvailableResponse {
        return EmailAvailableResponse(
            yourEmail = email,
            isAvailable = isAvailable,
            reasonCode = reasonCode,
            reasonMessage = messageResolver.resolve("$reasonCode.message", localeManager.getCurrentLocale()),
            reasonDescription = messageResolver.resolve("$reasonCode.description", localeManager.getCurrentLocale())
        )
    }
}
