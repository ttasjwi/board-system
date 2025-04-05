package com.ttasjwi.board.system.member.domain.service.fixture

import com.ttasjwi.board.system.global.exception.ErrorStatus
import com.ttasjwi.board.system.global.exception.fixture.customExceptionFixture
import com.ttasjwi.board.system.member.domain.service.NicknameManager

class NicknameManagerFixture : NicknameManager {

    companion object {
        const val ERROR_NICKNAME = "strange!ad;fklad!nickname"
    }

    val randomNames = listOf("random1", "random2")
    internal var randomNameCursor = 0

    override fun validate(nickname: String): Result<String> = kotlin.runCatching {
        if (nickname == ERROR_NICKNAME) {
            throw customExceptionFixture(
                status = ErrorStatus.BAD_REQUEST,
                code = "Error.InvalidNicknameFormat",
                args = emptyList(),
                source = "nickname",
                debugMessage = "닉네임 포맷 예외 - 픽스쳐"
            )
        }
        nickname
    }

    override fun createRandom(): String {
        val randomName = randomNames[randomNameCursor]
        randomNameCursor = (randomNameCursor + 1) % randomNames.size
        return randomName
    }
}
