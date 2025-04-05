package com.ttasjwi.board.system.member.domain.exception

import com.ttasjwi.board.system.global.exception.CustomException
import com.ttasjwi.board.system.global.exception.ErrorStatus

class InvalidEmailVerificationCodeException : CustomException(
    status = ErrorStatus.BAD_REQUEST,
    code = "Error.InvalidEmailVerificationCode",
    args = emptyList(),
    source = "code",
    debugMessage = "이메일 인증 코드가 올바르지 않습니다."
)
