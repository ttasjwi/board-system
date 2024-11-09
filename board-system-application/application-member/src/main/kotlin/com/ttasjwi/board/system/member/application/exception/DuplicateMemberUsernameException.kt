package com.ttasjwi.board.system.member.application.exception

import com.ttasjwi.board.system.core.exception.CustomException
import com.ttasjwi.board.system.core.exception.ErrorStatus

class DuplicateMemberUsernameException(
    username: String,
) : CustomException(
    status = ErrorStatus.CONFLICT,
    code = "Error.DuplicateMemberUsername",
    args = listOf(username),
    source = "username",
    debugMessage = "중복되는 사용자 아이디(username)의 회원이 존재합니다.(username=${username})"
)
