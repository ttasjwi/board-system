package com.ttasjwi.board.system.member.domain.exception

import com.ttasjwi.board.system.global.exception.CustomException
import com.ttasjwi.board.system.global.exception.ErrorStatus
import com.ttasjwi.board.system.member.domain.service.impl.PasswordManagerImpl

class InvalidPasswordFormatException : CustomException(
    status = ErrorStatus.BAD_REQUEST,
    code = "Error.InvalidPasswordFormat",
    args = listOf(PasswordManagerImpl.MIN_LENGTH, PasswordManagerImpl.MAX_LENGTH),
    source = "password",
    debugMessage =
        "패스워드는 ${PasswordManagerImpl.MIN_LENGTH} 자 이상 " +
                "${PasswordManagerImpl.MAX_LENGTH} 이하여야 합니다."
)
