package com.ttasjwi.board.system.auth.domain.exception

import com.ttasjwi.board.system.global.exception.CustomException
import com.ttasjwi.board.system.global.exception.ErrorStatus

class AccessDeniedException(
    cause: Throwable? = null,
) : CustomException(
    status = ErrorStatus.FORBIDDEN,
    code = "Error.AccessDenied",
    args = emptyList(),
    source = "credentials",
    debugMessage = "리소스에 접근할 수 없습니다. 해당 리소스에 접근할 권한이 없습니다.",
    cause = cause
)
