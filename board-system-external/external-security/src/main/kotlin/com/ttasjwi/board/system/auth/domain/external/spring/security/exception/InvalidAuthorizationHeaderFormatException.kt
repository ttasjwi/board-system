package com.ttasjwi.board.system.auth.domain.external.spring.security.exception

import com.ttasjwi.board.system.core.exception.CustomException
import com.ttasjwi.board.system.core.exception.ErrorStatus

class InvalidAuthorizationHeaderFormatException : CustomException(
    status = ErrorStatus.UNAUTHENTICATED,
    code = "Error.InvalidAuthorizationHeaderFormat",
    args = emptyList(),
    source = "authorizationHeader",
    debugMessage = "잘못된 Authorization 헤더 형식입니다. 토큰값을 Authorization 헤더에 'Bearer [토큰값]' 형식으로 보내주세요."
)
