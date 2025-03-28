package com.ttasjwi.board.system.member.application.processor

import com.ttasjwi.board.system.common.annotation.component.ApplicationProcessor
import com.ttasjwi.board.system.common.logging.getLogger
import com.ttasjwi.board.system.member.application.dto.NicknameAvailableQuery
import com.ttasjwi.board.system.member.application.usecase.NicknameAvailableResult
import com.ttasjwi.board.system.member.domain.service.MemberFinder
import com.ttasjwi.board.system.member.domain.service.NicknameCreator

@ApplicationProcessor
internal class NicknameAvailableProcessor(
    private val nicknameCreator: NicknameCreator,
    private val memberFinder: MemberFinder,
) {

    companion object {
        private val log = getLogger(NicknameAvailableProcessor::class.java)
    }

    fun checkNicknameAvailable(query: NicknameAvailableQuery): NicknameAvailableResult {
        val nickname = nicknameCreator.create(query.nickname)
            .getOrElse {
                log.info { "닉네임의 포맷이 유효하지 않습니다. (nickname = ${query.nickname})" }
                return NicknameAvailableResult(query.nickname, false, "NicknameAvailableCheck.InvalidFormat")
            }

        if (memberFinder.existsByNickname(nickname)) {
            log.info { "이미 사용 중인 닉네임입니다. (nickname = ${nickname.value})" }
            return NicknameAvailableResult(query.nickname, false, "NicknameAvailableCheck.Taken")
        }

        log.info { "사용 가능한 닉네임입니다. (nickname = ${nickname.value})" }
        return NicknameAvailableResult(query.nickname, true, "NicknameAvailableCheck.Available")
    }
}
