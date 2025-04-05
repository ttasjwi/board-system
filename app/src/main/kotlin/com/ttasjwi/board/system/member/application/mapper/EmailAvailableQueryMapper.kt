package com.ttasjwi.board.system.member.application.mapper

import com.ttasjwi.board.system.global.annotation.ApplicationQueryMapper
import com.ttasjwi.board.system.global.exception.NullArgumentException
import com.ttasjwi.board.system.global.logging.getLogger
import com.ttasjwi.board.system.member.application.dto.EmailAvailableQuery
import com.ttasjwi.board.system.member.application.usecase.EmailAvailableRequest

@ApplicationQueryMapper
internal class EmailAvailableQueryMapper {

    companion object {
        private val log = getLogger(EmailAvailableQueryMapper::class.java)
    }

    fun mapToQuery(request: EmailAvailableRequest): EmailAvailableQuery {
        log.info { "요청이 유효한지 확인합니다." }

        // email 이 null 이면 예외 발생
        if (request.email == null) {
            log.info { "이메일이 누락됐습니다." }
            throw NullArgumentException("email")
        }
        log.info { "입력으로 전달된 이메일이 확인됐습니다. (email = ${request.email})" }
        return EmailAvailableQuery(request.email)
    }
}
