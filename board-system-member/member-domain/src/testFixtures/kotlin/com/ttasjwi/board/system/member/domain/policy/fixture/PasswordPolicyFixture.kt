package com.ttasjwi.board.system.member.domain.policy.fixture

import com.ttasjwi.board.system.common.exception.ErrorStatus
import com.ttasjwi.board.system.common.exception.fixture.customExceptionFixture
import com.ttasjwi.board.system.member.domain.policy.PasswordPolicy
import java.util.*

class PasswordPolicyFixture : PasswordPolicy {

    companion object {
        const val ERROR_PASSWORD = "2#!"
        const val RANDOM_PASSWORD_LENGTH = 16
    }

    override fun validate(rawPassword: String): Result<String> = kotlin.runCatching {
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

    override fun createRandomPassword(): String {
        return UUID.randomUUID().toString().replace("-", "")
            .substring(0, RANDOM_PASSWORD_LENGTH)
    }
}
