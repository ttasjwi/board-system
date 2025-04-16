package com.ttasjwi.board.system.user.domain.exception

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class DuplicateMemberEmailException(
    email: String,
) : CustomException(
    status = ErrorStatus.CONFLICT,
    code = "Error.DuplicateMemberEmail",
    args = listOf(email),
    source = "email",
    debugMessage = "중복되는 이메일의 회원이 존재합니다.(email=${email})"
)
