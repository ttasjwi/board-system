package com.ttasjwi.board.system.member.domain.exception

import com.ttasjwi.board.system.core.exception.CustomException
import com.ttasjwi.board.system.core.exception.ErrorStatus
import com.ttasjwi.board.system.member.domain.model.RawPassword

class InvalidPasswordFormatException : CustomException(
    status = ErrorStatus.BAD_REQUEST,
    code = "Error.InvalidPasswordFormat",
    args = listOf(RawPassword.MIN_LENGTH, RawPassword.MAX_LENGTH),
    source = "password",
    debugMessage =
    "패스워드는 ${RawPassword.MIN_LENGTH} 자 이상 " +
            "${RawPassword.MAX_LENGTH} 이하여야 합니다."
)
