package com.ttasjwi.board.system.application.member.processor

import com.ttasjwi.board.system.application.member.dto.NicknameAvailableQuery
import com.ttasjwi.board.system.application.member.usecase.NicknameAvailableResponse
import com.ttasjwi.board.system.global.annotation.ApplicationProcessor
import com.ttasjwi.board.system.global.locale.LocaleManager
import com.ttasjwi.board.system.global.logging.getLogger
import com.ttasjwi.board.system.global.message.MessageResolver
import com.ttasjwi.board.system.member.domain.service.MemberFinder
import com.ttasjwi.board.system.member.domain.service.NicknameManager

@ApplicationProcessor
internal class NicknameAvailableProcessor(
    private val nicknameManager: NicknameManager,
    private val memberFinder: MemberFinder,
    private val messageResolver: MessageResolver,
    private val localeManager: LocaleManager,
) {

    companion object {
        private val log = getLogger(NicknameAvailableProcessor::class.java)
    }

    fun checkNicknameAvailable(query: NicknameAvailableQuery): NicknameAvailableResponse {
        val nickname = nicknameManager.validate(query.nickname)
            .getOrElse {
                log.info { "닉네임의 포맷이 유효하지 않습니다. (nickname = ${query.nickname})" }
                return makeResponse(query.nickname, false, "NicknameAvailableCheck.InvalidFormat")
            }

        if (memberFinder.existsByNickname(nickname)) {
            log.info { "이미 사용 중인 닉네임입니다. (nickname = ${nickname})" }
            return makeResponse(query.nickname, false, "NicknameAvailableCheck.Taken")
        }

        log.info { "사용 가능한 닉네임입니다. (nickname = ${nickname})" }
        return makeResponse(query.nickname, true, "NicknameAvailableCheck.Available")
    }

    private fun makeResponse(nickname: String, isAvailable: Boolean, reasonCode: String): NicknameAvailableResponse {
        return NicknameAvailableResponse(
            yourNickname = nickname,
            isAvailable = isAvailable,
            reasonCode = reasonCode,
            reasonMessage = messageResolver.resolve("$reasonCode.message", localeManager.getCurrentLocale()),
            reasonDescription = messageResolver.resolve("$reasonCode.description", localeManager.getCurrentLocale())
        )
    }
}
