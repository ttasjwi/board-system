package com.ttasjwi.board.system.common.auth

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class RefreshTokenExpiredException(
    debugMessage: String,
) : CustomException(
    status = ErrorStatus.UNAUTHENTICATED,
    code = "Error.RefreshTokenExpired",
    args = emptyList(),
    source = "refreshToken",
    debugMessage = debugMessage
)
