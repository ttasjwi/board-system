package com.ttasjwi.board.system.external.spring.security.exception

import com.ttasjwi.board.system.core.exception.CustomException
import com.ttasjwi.board.system.core.exception.ErrorStatus

class InvalidOAuth2ProviderIdException(
    providerId: String,
    cause: Throwable,
): CustomException(
    status = ErrorStatus.BAD_REQUEST,
    code = "Error.InvalidOAuth2ProviderId",
    args = listOf(providerId),
    source = "providerId",
    debugMessage = "우리 서비스에서 연동하는 소셜서비스 제공자 id가 아닙니다. (providerId= ${providerId})",
    cause = cause,
)
