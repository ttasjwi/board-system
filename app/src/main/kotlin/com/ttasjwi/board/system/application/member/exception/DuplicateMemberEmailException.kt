package com.ttasjwi.board.system.application.member.exception

import com.ttasjwi.board.system.global.exception.CustomException
import com.ttasjwi.board.system.global.exception.ErrorStatus

class DuplicateMemberEmailException(
    email: String,
) : CustomException(
    status = ErrorStatus.CONFLICT,
    code = "Error.DuplicateMemberEmail",
    args = listOf(email),
    source = "email",
    debugMessage = "중복되는 이메일의 회원이 존재합니다.(email=${email})"
)
