package com.ttasjwi.board.system.auth.domain.exception

import com.ttasjwi.board.system.core.exception.CustomException
import com.ttasjwi.board.system.core.exception.ErrorStatus

class InvalidAccessTokenFormatException(
    cause: Throwable? = null,
) : CustomException(
    status = ErrorStatus.UNAUTHENTICATED,
    code = "Error.InvalidAccessTokenFormat",
    args = emptyList(),
    source = "accessToken",
    debugMessage = "액세스 토큰 포맷이 유효하지 않습니다. 토큰값이 잘못됐거나, 액세스 토큰이 아닙니다.",
    cause = cause,
)
