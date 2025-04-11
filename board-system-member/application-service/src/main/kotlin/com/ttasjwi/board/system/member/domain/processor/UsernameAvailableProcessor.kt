package com.ttasjwi.board.system.member.domain.processor

import com.ttasjwi.board.system.common.annotation.component.ApplicationProcessor
import com.ttasjwi.board.system.common.logger.getLogger
import com.ttasjwi.board.system.common.message.MessageResolver
import com.ttasjwi.board.system.member.domain.UsernameAvailableResponse
import com.ttasjwi.board.system.member.domain.dto.UsernameAvailableQuery
import com.ttasjwi.board.system.member.domain.policy.UsernamePolicy
import com.ttasjwi.board.system.member.domain.port.MemberPersistencePort
import java.util.*

@ApplicationProcessor
internal class UsernameAvailableProcessor(
    private val usernamePolicy: UsernamePolicy,
    private val memberPersistencePort: MemberPersistencePort,
    private val messageResolver: MessageResolver,
) {

    companion object {
        private val log = getLogger(UsernameAvailableProcessor::class.java)
    }

    fun checkUsernameAvailable(query: UsernameAvailableQuery): UsernameAvailableResponse {
        val username = usernamePolicy.validate(query.username)
            .getOrElse {
                log.info { "사용자 아이디(username)의 포맷이 유효하지 않습니다. (username = ${query.username})" }
                return makeResponse(query.username, false, "UsernameAvailableCheck.InvalidFormat", query.locale)
            }

        if (memberPersistencePort.existsByUsername(username)) {
            log.info { "이미 사용 중인 사용자 아이디(username)입니다. (username = ${username})" }
            return makeResponse(query.username, false, "UsernameAvailableCheck.Taken", query.locale)
        }

        log.info { "사용 가능한 사용자아이디(username)입니다. (username = ${username})" }
        return makeResponse(query.username, true, "UsernameAvailableCheck.Available", query.locale)
    }

    private fun makeResponse(
        username: String,
        isAvailable: Boolean,
        reasonCode: String,
        locale: Locale
    ): UsernameAvailableResponse {
        return UsernameAvailableResponse(
            yourUsername = username,
            isAvailable = isAvailable,
            reasonCode = reasonCode,
            reasonMessage = messageResolver.resolve("$reasonCode.message", locale),
            reasonDescription = messageResolver.resolve("$reasonCode.description", locale)
        )
    }
}
