package com.ttasjwi.board.system.member.domain.processor

import com.ttasjwi.board.system.common.annotation.component.ApplicationProcessor
import com.ttasjwi.board.system.common.logger.getLogger
import com.ttasjwi.board.system.common.message.MessageResolver
import com.ttasjwi.board.system.member.domain.NicknameAvailableResponse
import com.ttasjwi.board.system.member.domain.dto.NicknameAvailableQuery
import com.ttasjwi.board.system.member.domain.policy.NicknamePolicy
import com.ttasjwi.board.system.member.domain.port.MemberPersistencePort
import java.util.*

@ApplicationProcessor
internal class NicknameAvailableProcessor(
    private val nicknamePolicy: NicknamePolicy,
    private val memberPersistencePort: MemberPersistencePort,
    private val messageResolver: MessageResolver,
) {

    companion object {
        private val log = getLogger(NicknameAvailableProcessor::class.java)
    }

    fun checkNicknameAvailable(query: NicknameAvailableQuery): NicknameAvailableResponse {
        val nickname = nicknamePolicy.validate(query.nickname)
            .getOrElse {
                log.info { "닉네임의 포맷이 유효하지 않습니다. (nickname = ${query.nickname})" }
                return makeResponse(query.nickname, false, "NicknameAvailableCheck.InvalidFormat", query.locale)
            }

        if (memberPersistencePort.existsByNickname(nickname)) {
            log.info { "이미 사용 중인 닉네임입니다. (nickname = ${nickname})" }
            return makeResponse(query.nickname, false, "NicknameAvailableCheck.Taken", query.locale)
        }

        log.info { "사용 가능한 닉네임입니다. (nickname = ${nickname})" }
        return makeResponse(query.nickname, true, "NicknameAvailableCheck.Available", query.locale)
    }

    private fun makeResponse(
        nickname: String,
        isAvailable: Boolean,
        reasonCode: String,
        locale: Locale
    ): NicknameAvailableResponse {
        return NicknameAvailableResponse(
            yourNickname = nickname,
            isAvailable = isAvailable,
            reasonCode = reasonCode,
            reasonMessage = messageResolver.resolve("$reasonCode.message", locale),
            reasonDescription = messageResolver.resolve("$reasonCode.description", locale)
        )
    }
}
