package com.ttasjwi.board.system.member.domain.exception

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class InvalidEmailFormatException(
    emailValue: String,
) : CustomException(
    status = ErrorStatus.BAD_REQUEST,
    code = "Error.InvalidEmailFormat",
    args = listOf(emailValue),
    source = "email",
    debugMessage = "이메일의 형식이 올바르지 않습니다. (email = $emailValue)",
)

