package com.ttasjwi.board.system.auth.domain.exception

import com.ttasjwi.board.system.core.exception.CustomException
import com.ttasjwi.board.system.core.exception.ErrorStatus

class UnauthenticatedException(
    cause: Throwable? = null,
) : CustomException(
    status = ErrorStatus.UNAUTHENTICATED,
    code = "Error.Unauthenticated",
    args = emptyList(),
    source = "credentials",
    debugMessage = "리소스에 접근할 수 없습니다. 이 리소스에 접근하기 위해서는 인증이 필요합니다.",
    cause = cause
)
