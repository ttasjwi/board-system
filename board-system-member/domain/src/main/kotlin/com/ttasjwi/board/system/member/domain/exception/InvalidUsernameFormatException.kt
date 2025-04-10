package com.ttasjwi.board.system.member.domain.exception

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus
import com.ttasjwi.board.system.member.domain.policy.UsernamePolicyImpl

class InvalidUsernameFormatException(
    usernameValue: String,
) : CustomException(
    status = ErrorStatus.BAD_REQUEST,
    code = "Error.InvalidUsernameFormat",
    args = listOf(UsernamePolicyImpl.MIN_LENGTH, UsernamePolicyImpl.MAX_LENGTH, usernameValue),
    source = "username",
    debugMessage = "" +
            "사용자아이디(username)는 ${UsernamePolicyImpl.MIN_LENGTH} 자 이상 " +
            "${UsernamePolicyImpl.MAX_LENGTH} 이하여야 하며, 영어소문자/숫자/언더바 만 허용됩니다. " +
            "(username = $usernameValue)"
)
