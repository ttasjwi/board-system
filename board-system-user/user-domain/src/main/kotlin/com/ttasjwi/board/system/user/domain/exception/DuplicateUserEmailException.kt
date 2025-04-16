package com.ttasjwi.board.system.user.domain.exception

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class DuplicateUserEmailException(
    email: String,
) : CustomException(
    status = ErrorStatus.CONFLICT,
    code = "Error.DuplicateUserEmail",
    args = listOf(email),
    source = "email",
    debugMessage = "중복되는 이메일의 사용자가 존재합니다.(email=${email})"
)
