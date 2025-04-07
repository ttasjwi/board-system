package com.ttasjwi.board.system.domain.member.service

import com.ttasjwi.board.system.global.exception.ErrorStatus
import com.ttasjwi.board.system.global.exception.fixture.customExceptionFixture

class EmailManagerFixture : EmailManager {

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
