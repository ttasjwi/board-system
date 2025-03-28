package com.ttasjwi.board.system.member.application.processor

import com.ttasjwi.board.system.common.annotation.component.ApplicationProcessor
import com.ttasjwi.board.system.common.logging.getLogger
import com.ttasjwi.board.system.member.application.dto.UsernameAvailableQuery
import com.ttasjwi.board.system.member.application.usecase.UsernameAvailableResult
import com.ttasjwi.board.system.member.domain.service.MemberFinder
import com.ttasjwi.board.system.member.domain.service.UsernameCreator

@ApplicationProcessor
internal class UsernameAvailableProcessor(
    private val usernameCreator: UsernameCreator,
    private val memberFinder: MemberFinder,
) {

    companion object {
        private val log = getLogger(UsernameAvailableProcessor::class.java)
    }


    fun checkUsernameAvailable(query: UsernameAvailableQuery): UsernameAvailableResult {
        val username = usernameCreator.create(query.username)
            .getOrElse {
                log.info { "사용자 아이디(username)의 포맷이 유효하지 않습니다. (username = ${query.username})" }
                return UsernameAvailableResult(query.username, false, "UsernameAvailableCheck.InvalidFormat")
            }

        if (memberFinder.existsByUsername(username)) {
            log.info { "이미 사용 중인 사용자 아이디(username)입니다. (username = ${username.value})" }
            return UsernameAvailableResult(query.username, false, "UsernameAvailableCheck.Taken")
        }

        log.info { "사용 가능한 사용자아이디(username)입니다. (username = ${username.value})" }
        return UsernameAvailableResult(query.username, true, "UsernameAvailableCheck.Available")
    }
}
