package com.ttasjwi.board.system.auth.application.exception

import com.ttasjwi.board.system.core.exception.CustomException
import com.ttasjwi.board.system.core.exception.ErrorStatus

/**
 * 로그인 예외는 사용자에게 구체적인 사유를 밝히는 것은 보안상 위험성이 있습니다.
 * 따라서 서버 내부에서 확인할 수 있는 수준에서 디버깅 메시지만 예외에 담아 생성하세요.
 */
class LoginFailureException(
    debugMessage: String,
) : CustomException(
    status = ErrorStatus.UNAUTHENTICATED,
    code = "Error.LoginFailure",
    args = emptyList(),
    source = "credentials",
    debugMessage = debugMessage
)
