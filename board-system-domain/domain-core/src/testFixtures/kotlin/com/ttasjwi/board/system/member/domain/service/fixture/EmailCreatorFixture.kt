package com.ttasjwi.board.system.member.domain.service.fixture

import com.ttasjwi.board.system.core.exception.ErrorStatus
import com.ttasjwi.board.system.core.exception.fixture.customExceptionFixture
import com.ttasjwi.board.system.member.domain.model.Email
import com.ttasjwi.board.system.member.domain.model.fixture.emailFixture
import com.ttasjwi.board.system.member.domain.service.EmailCreator

class EmailCreatorFixture : EmailCreator {

    companion object {
        const val ERROR_EMAIL = "erro!@gmail.com"
    }

    override fun create(value: String): Result<Email> = kotlin.runCatching {
        if (value == ERROR_EMAIL) {
            throw customExceptionFixture(
                status = ErrorStatus.INVALID_ARGUMENT,
                code = "Error.InvalidEmailFormat",
                args = emptyList(),
                source = "email",
                debugMessage = "이메일 포맷 예외 - 픽스쳐"
            )
        }
        emailFixture(value)
    }
}
