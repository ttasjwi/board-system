package com.ttasjwi.board.system.member.domain.service.fixture

import com.ttasjwi.board.system.global.exception.ErrorStatus
import com.ttasjwi.board.system.global.exception.fixture.customExceptionFixture
import com.ttasjwi.board.system.member.domain.service.PasswordManager
import java.util.*

class PasswordManagerFixture : PasswordManager {

    companion object {
        const val ERROR_PASSWORD = "2#!"
        const val RANDOM_PASSWORD_LENGTH = 16
    }

    override fun validateRawPassword(rawPassword: String): Result<String> = kotlin.runCatching {
        if (rawPassword == ERROR_PASSWORD) {
            throw customExceptionFixture(
                status = ErrorStatus.BAD_REQUEST,
                code = "Error.InvalidPasswordFormat",
                args = emptyList(),
                source = "password",
                debugMessage = "패스워드 포맷 예외 - 픽스쳐"
            )
        }
        rawPassword
    }

    override fun createRandomRawPassword(): String {
        return UUID.randomUUID().toString().replace("-", "")
            .substring(0, RANDOM_PASSWORD_LENGTH)
    }

    override fun encode(rawPassword: String): String {
        return rawPassword
    }

    override fun matches(rawPassword: String, encodedPassword: String): Boolean {
        return rawPassword == encodedPassword
    }
}
