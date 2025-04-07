package com.ttasjwi.board.system.member.application.mapper

import com.ttasjwi.board.system.common.exception.NullArgumentException
import com.ttasjwi.board.system.common.logger.getLogger
import com.ttasjwi.board.system.member.application.dto.NicknameAvailableQuery
import com.ttasjwi.board.system.member.application.usecase.NicknameAvailableRequest
import org.springframework.stereotype.Component

@Component
internal class NicknameAvailableQueryMapper {

    companion object {
        private val log = getLogger(NicknameAvailableQueryMapper::class.java)
    }

    fun mapToQuery(request: NicknameAvailableRequest): NicknameAvailableQuery {
        log.info { "요청이 유효한지 확인합니다." }

        // 닉네임이 null 이면 예외 발생
        if (request.nickname == null) {
            log.info { "닉네임이 누락됐습니다." }
            throw NullArgumentException("nickname")
        }
        log.info { "입력으로 전달된 닉네임이 확인됐습니다. (nickname = ${request.nickname})" }
        return NicknameAvailableQuery(request.nickname)
    }
}
