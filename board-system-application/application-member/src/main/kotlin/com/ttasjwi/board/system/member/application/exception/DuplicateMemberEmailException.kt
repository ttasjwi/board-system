package com.ttasjwi.board.system.member.application.exception

import com.ttasjwi.board.system.core.exception.CustomException
import com.ttasjwi.board.system.core.exception.ErrorStatus

class DuplicateMemberEmailException(
    email: String,
) : CustomException(
    status = ErrorStatus.CONFLICT,
    code = "Error.DuplicateMemberEmail",
    args = listOf(email),
    source = "email",
    debugMessage = "중복되는 이메일의 회원이 존재합니다.(email=${email})"
)
