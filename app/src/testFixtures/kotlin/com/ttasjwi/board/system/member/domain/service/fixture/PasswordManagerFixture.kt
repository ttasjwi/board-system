package com.ttasjwi.board.system.member.domain.service.fixture

import com.ttasjwi.board.system.common.exception.ErrorStatus
import com.ttasjwi.board.system.common.exception.fixture.customExceptionFixture
import com.ttasjwi.board.system.member.domain.model.EncodedPassword
import com.ttasjwi.board.system.member.domain.model.RawPassword
import com.ttasjwi.board.system.member.domain.model.fixture.encodedPasswordFixture
import com.ttasjwi.board.system.member.domain.model.fixture.rawPasswordFixture
import com.ttasjwi.board.system.member.domain.service.PasswordManager
import java.util.*

class PasswordManagerFixture : PasswordManager {

    companion object {
        const val ERROR_PASSWORD = "2#!"
        const val RANDOM_PASSWORD_LENGTH = 16
    }

    override fun createRawPassword(value: String): Result<RawPassword> = kotlin.runCatching {
        if (value == ERROR_PASSWORD) {
            throw customExceptionFixture(
                status = ErrorStatus.BAD_REQUEST,
                code = "Error.InvalidPasswordFormat",
                args = emptyList(),
                source = "password",
                debugMessage = "패스워드 포맷 예외 - 픽스쳐"
            )
        }
        rawPasswordFixture(value)
    }

    override fun createRandomRawPassword(): RawPassword {
        return rawPasswordFixture(
            UUID.randomUUID().toString().replace("-", "")
                .substring(0, RANDOM_PASSWORD_LENGTH)
        )
    }

    override fun encode(rawPassword: RawPassword): EncodedPassword {
        return encodedPasswordFixture(rawPassword.value)
    }

    override fun matches(rawPassword: RawPassword, encodedPassword: EncodedPassword): Boolean {
        return rawPassword.value == encodedPassword.value
    }
}
