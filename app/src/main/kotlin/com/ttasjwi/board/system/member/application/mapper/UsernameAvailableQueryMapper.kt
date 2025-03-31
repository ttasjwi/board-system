package com.ttasjwi.board.system.member.application.mapper

import com.ttasjwi.board.system.common.annotation.component.ApplicationQueryMapper
import com.ttasjwi.board.system.common.exception.NullArgumentException
import com.ttasjwi.board.system.common.logging.getLogger
import com.ttasjwi.board.system.member.application.dto.UsernameAvailableQuery
import com.ttasjwi.board.system.member.application.usecase.UsernameAvailableRequest

@ApplicationQueryMapper
internal class UsernameAvailableQueryMapper {

    companion object {
        private val log = getLogger(UsernameAvailableQueryMapper::class.java)
    }

    fun mapToQuery(request: UsernameAvailableRequest): UsernameAvailableQuery {
        log.info { "요청이 유효한지 확인합니다." }

        // username 이 null 이면 예외 발생
        if (request.username == null) {
            log.info { "사용자 아이디(username)가 누락됐습니다." }
            throw NullArgumentException("username")
        }
        log.info { "입력으로 전달된 사용자아이디가 확인됐습니다. (username = ${request.username})" }
        return UsernameAvailableQuery(request.username)
    }
}
