package com.ttasjwi.board.system.user.domain.exception

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class EmailNotVerifiedException(
    val email: String,
) : CustomException(
    status = ErrorStatus.BAD_REQUEST,
    code = "Error.EmailNotVerified",
    args = listOf(email),
    source = "emailVerification",
    debugMessage = "이 이메일은 인증되지 않았습니다. 인증을 먼저 수행해주세요. (email=${email})"
)
