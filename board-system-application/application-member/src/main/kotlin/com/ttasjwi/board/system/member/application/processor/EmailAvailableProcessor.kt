package com.ttasjwi.board.system.member.application.processor

import com.ttasjwi.board.system.core.annotation.component.ApplicationProcessor
import com.ttasjwi.board.system.logging.getLogger
import com.ttasjwi.board.system.member.application.dto.EmailAvailableQuery
import com.ttasjwi.board.system.member.application.usecase.EmailAvailableResult
import com.ttasjwi.board.system.member.domain.service.EmailCreator
import com.ttasjwi.board.system.member.domain.service.MemberFinder

@ApplicationProcessor
internal class EmailAvailableProcessor(
    private val emailCreator: EmailCreator,
    private val memberFinder: MemberFinder,
) {

    companion object {
        private val log = getLogger(EmailAvailableProcessor::class.java)
    }

    fun checkEmailAvailable(query: EmailAvailableQuery): EmailAvailableResult {
        val email = emailCreator.create(query.email)
            .getOrElse {
                log.info { "이메일의 포맷이 유효하지 않습니다. (email = ${query.email})" }
                return EmailAvailableResult(query.email, false, "EmailAvailableCheck.InvalidFormat")
            }

        if (memberFinder.existsByEmail(email)) {
            log.info { "이미 사용 중인 이메일입니다. (email = ${email.value})" }
            return EmailAvailableResult(query.email, false, "EmailAvailableCheck.Taken")
        }

        log.info { "사용 가능한 이메일입니다. (email = ${email.value})" }
        return EmailAvailableResult(query.email, true, "EmailAvailableCheck.Available")
    }
}
