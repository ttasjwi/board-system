package com.ttasjwi.board.system.member.application.processor

import com.ttasjwi.board.system.common.annotation.component.ApplicationProcessor
import com.ttasjwi.board.system.common.locale.LocaleManager
import com.ttasjwi.board.system.common.logging.getLogger
import com.ttasjwi.board.system.common.message.MessageResolver
import com.ttasjwi.board.system.member.application.dto.NicknameAvailableQuery
import com.ttasjwi.board.system.member.application.usecase.NicknameAvailableResponse
import com.ttasjwi.board.system.member.domain.service.MemberFinder
import com.ttasjwi.board.system.member.domain.service.NicknameCreator

@ApplicationProcessor
internal class NicknameAvailableProcessor(
    private val nicknameCreator: NicknameCreator,
    private val memberFinder: MemberFinder,
    private val messageResolver: MessageResolver,
    private val localeManager: LocaleManager,
) {

    companion object {
        private val log = getLogger(NicknameAvailableProcessor::class.java)
    }

    fun checkNicknameAvailable(query: NicknameAvailableQuery): NicknameAvailableResponse {
        val nickname = nicknameCreator.create(query.nickname)
            .getOrElse {
                log.info { "닉네임의 포맷이 유효하지 않습니다. (nickname = ${query.nickname})" }
                return makeResponse(query.nickname, false, "NicknameAvailableCheck.InvalidFormat")
            }

        if (memberFinder.existsByNickname(nickname)) {
            log.info { "이미 사용 중인 닉네임입니다. (nickname = ${nickname.value})" }
            return makeResponse(query.nickname, false, "NicknameAvailableCheck.Taken")
        }

        log.info { "사용 가능한 닉네임입니다. (nickname = ${nickname.value})" }
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
