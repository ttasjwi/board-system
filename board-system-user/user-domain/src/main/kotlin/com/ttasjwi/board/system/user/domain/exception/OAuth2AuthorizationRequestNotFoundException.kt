package com.ttasjwi.board.system.user.domain.exception

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus

class OAuth2AuthorizationRequestNotFoundException(
    state: String
) : CustomException(
    status = ErrorStatus.UNAUTHENTICATED,
    code = "Error.OAuth2AuthorizationRequestNotFound",
    args = listOf(state),
    source = "state",
    debugMessage = "우리 서비스를 통해 소셜 서비스 로그인 요청을 한 내역이 없습니다. (state = $state)",
)
