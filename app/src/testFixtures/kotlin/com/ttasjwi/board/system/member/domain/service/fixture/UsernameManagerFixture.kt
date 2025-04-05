package com.ttasjwi.board.system.member.domain.service.fixture

import com.ttasjwi.board.system.global.exception.ErrorStatus
import com.ttasjwi.board.system.global.exception.fixture.customExceptionFixture
import com.ttasjwi.board.system.member.domain.service.UsernameManager

class UsernameManagerFixture : UsernameManager {

    companion object {
        const val ERROR_USERNAME = "strange!ad;fklad!username"
    }

    val randomNames = listOf("random1", "random2")
    internal var randomNameCursor = 0

    override fun validate(username: String): Result<String> = runCatching {
        if (username == ERROR_USERNAME) {
            throw customExceptionFixture(
                status = ErrorStatus.BAD_REQUEST,
                code = "Error.InvalidUsernameFormat",
                args = emptyList(),
                source = "username",
                debugMessage = "사용자아이디(username) 포맷 예외 - 픽스쳐"
            )
        }
        username
    }

    override fun createRandom(): String {
        val randomName = randomNames[randomNameCursor]
        randomNameCursor = (randomNameCursor + 1) % randomNames.size
        return randomName
    }
}
