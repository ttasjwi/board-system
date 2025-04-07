package com.ttasjwi.board.system.domain.member.exception

import com.ttasjwi.board.system.global.exception.CustomException
import com.ttasjwi.board.system.global.exception.ErrorStatus

class InvalidEmailFormatException(
    emailValue: String,
) : CustomException(
    status = ErrorStatus.BAD_REQUEST,
    code = "Error.InvalidEmailFormat",
    args = listOf(emailValue),
    source = "email",
    debugMessage = "이메일의 형식이 올바르지 않습니다. (email = $emailValue)",
)

