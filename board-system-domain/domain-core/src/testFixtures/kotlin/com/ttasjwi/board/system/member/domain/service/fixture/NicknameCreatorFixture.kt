package com.ttasjwi.board.system.member.domain.service.fixture

import com.ttasjwi.board.system.core.exception.ErrorStatus
import com.ttasjwi.board.system.core.exception.fixture.customExceptionFixture
import com.ttasjwi.board.system.member.domain.model.Nickname
import com.ttasjwi.board.system.member.domain.model.fixture.nicknameFixture
import com.ttasjwi.board.system.member.domain.service.NicknameCreator

class NicknameCreatorFixture : NicknameCreator {

    companion object {
        const val ERROR_NICKNAME = "strange!ad;fklad!nickname"
    }

    override fun create(value: String): Result<Nickname> = kotlin.runCatching {
        if (value == ERROR_NICKNAME) {
            throw customExceptionFixture(
                status = ErrorStatus.INVALID_ARGUMENT,
                code = "Error.InvalidNicknameFormat",
                args = emptyList(),
                source = "nickname",
                debugMessage = "닉네임 포맷 예외 - 픽스쳐"
            )
        }
        nicknameFixture(value)
    }
}
