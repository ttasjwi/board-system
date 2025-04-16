package com.ttasjwi.board.system.user.domain.exception

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class DuplicateUserUsernameException(
    username: String,
) : CustomException(
    status = ErrorStatus.CONFLICT,
    code = "Error.DuplicateUserUsername",
    args = listOf(username),
    source = "username",
    debugMessage = "중복되는 사용자 아이디(username)의 사용자가 존재합니다.(username=${username})"
)
