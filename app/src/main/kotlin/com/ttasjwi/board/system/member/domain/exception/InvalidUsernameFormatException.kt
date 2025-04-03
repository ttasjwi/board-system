package com.ttasjwi.board.system.member.domain.exception

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus
import com.ttasjwi.board.system.member.domain.service.impl.UsernameManagerImpl

class InvalidUsernameFormatException(
    usernameValue: String,
) : CustomException(
    status = ErrorStatus.BAD_REQUEST,
    code = "Error.InvalidUsernameFormat",
    args = listOf(UsernameManagerImpl.MIN_LENGTH, UsernameManagerImpl.MAX_LENGTH, usernameValue),
    source = "username",
    debugMessage = "" +
            "사용자아이디(username)는 ${UsernameManagerImpl.MIN_LENGTH} 자 이상 " +
            "${UsernameManagerImpl.MAX_LENGTH} 이하여야 하며, 영어소문자/숫자/언더바 만 허용됩니다. " +
            "(username = $usernameValue)"
)
