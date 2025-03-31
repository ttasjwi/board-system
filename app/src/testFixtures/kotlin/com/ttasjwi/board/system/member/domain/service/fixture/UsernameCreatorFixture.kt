package com.ttasjwi.board.system.member.domain.service.fixture

import com.ttasjwi.board.system.common.exception.ErrorStatus
import com.ttasjwi.board.system.common.exception.fixture.customExceptionFixture
import com.ttasjwi.board.system.member.domain.model.Username
import com.ttasjwi.board.system.member.domain.model.fixture.usernameFixture
import com.ttasjwi.board.system.member.domain.service.UsernameCreator

class UsernameCreatorFixture : UsernameCreator {

    companion object {
        const val ERROR_USERNAME = "strange!ad;fklad!username"
    }

    val randomNames = listOf("random1", "random2")
    internal var randomNameCursor = 0

    override fun create(value: String): Result<Username> = kotlin.runCatching {
        if (value == ERROR_USERNAME) {
            throw customExceptionFixture(
                status = ErrorStatus.BAD_REQUEST,
                code = "Error.InvalidUsernameFormat",
                args = emptyList(),
                source = "username",
                debugMessage = "사용자아이디(username) 포맷 예외 - 픽스쳐"
            )
        }
        usernameFixture(value)
    }

    override fun createRandom(): Username {
        val randomName = randomNames[randomNameCursor]

        randomNameCursor++

        if (randomNameCursor == randomNames.size) {
            randomNameCursor = 0
        }

        return usernameFixture(randomName)
    }
}
