package com.ttasjwi.board.system.common.auth.infra.token

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class InvalidRefreshTokenFormatException(
    cause: Throwable? = null,
) : CustomException(
    status = ErrorStatus.UNAUTHENTICATED,
    code = "Error.InvalidRefreshTokenFormat",
    args = emptyList(),
    source = "refreshToken",
    debugMessage = "리프레시 토큰 포맷이 유효하지 않습니다. 토큰값이 잘못됐거나, 리프레시 토큰이 아닙니다.",
    cause = cause
)
