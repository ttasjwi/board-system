package com.ttasjwi.board.system.member.infra.spring.security.oauth2.exception

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class OAuth2AuthorizationRequestNotFoundException(
    cause: Throwable,
) : CustomException(
    status = ErrorStatus.UNAUTHENTICATED,
    code = "Error.OAuth2AuthorizationRequestNotFound",
    args = emptyList(),
    source = "oauth2AuthorizationRequest",
    debugMessage = "우리 서비스를 통해 소셜 서비스 로그인 요청을 한 내역이 없습니다.",
    cause = cause,
)
