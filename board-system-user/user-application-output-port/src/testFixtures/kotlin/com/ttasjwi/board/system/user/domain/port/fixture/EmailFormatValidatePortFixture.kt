package com.ttasjwi.board.system.user.domain.port.fixture

import com.ttasjwi.board.system.common.exception.ErrorStatus
import com.ttasjwi.board.system.common.exception.fixture.customExceptionFixture
import com.ttasjwi.board.system.user.domain.port.EmailFormatValidatePort

class EmailFormatValidatePortFixture : EmailFormatValidatePort {

    companion object {
        const val ERROR_EMAIL = "erro!@gmail.com"
    }

    override fun validate(email: String): Result<String> = kotlin.runCatching {
        if (email != ERROR_EMAIL) {
            return Result.success(email)
        }
        return Result.failure(
            customExceptionFixture(
                status = ErrorStatus.BAD_REQUEST,
                code = "Error.InvalidEmailFormat",
                args = emptyList(),
                source = "email",
                debugMessage = "이메일 포맷 예외 - 픽스쳐"
            )
        )
    }
}
