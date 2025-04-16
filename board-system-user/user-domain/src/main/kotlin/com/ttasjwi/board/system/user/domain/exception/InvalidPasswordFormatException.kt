package com.ttasjwi.board.system.user.domain.exception

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus
import com.ttasjwi.board.system.user.domain.policy.PasswordPolicyImpl

class InvalidPasswordFormatException : CustomException(
    status = ErrorStatus.BAD_REQUEST,
    code = "Error.InvalidPasswordFormat",
    args = listOf(PasswordPolicyImpl.MIN_LENGTH, PasswordPolicyImpl.MAX_LENGTH),
    source = "password",
    debugMessage =
        "패스워드는 ${PasswordPolicyImpl.MIN_LENGTH} 자 이상 " +
                "${PasswordPolicyImpl.MAX_LENGTH} 자 이하여야 합니다."
)
