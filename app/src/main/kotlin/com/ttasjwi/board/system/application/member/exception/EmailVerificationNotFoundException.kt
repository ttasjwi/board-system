package com.ttasjwi.board.system.application.member.exception

import com.ttasjwi.board.system.global.exception.CustomException
import com.ttasjwi.board.system.global.exception.ErrorStatus

class EmailVerificationNotFoundException(
    email: String,
) : CustomException(
    status = ErrorStatus.BAD_REQUEST,
    code = "Error.EmailVerificationNotFound",
    args = listOf(email),
    source = "emailVerification",
    debugMessage = "이메일 인증이 만료됐거나 인증이 존재하지 않습니다. 다시 인증 절차를 시작해주세요. (email=${email})"
)
