package com.ttasjwi.board.system.global.springsecurity.oauth2.exception

import com.ttasjwi.board.system.global.exception.CustomException
import com.ttasjwi.board.system.global.exception.ErrorStatus

class InvalidOAuth2ProviderIdException(
    providerId: String,
    cause: Throwable,
) : CustomException(
    status = ErrorStatus.BAD_REQUEST,
    code = "Error.InvalidOAuth2ProviderId",
    args = listOf(providerId),
    source = "providerId",
    debugMessage = "우리 서비스에서 연동하는 소셜서비스 제공자 id가 아닙니다. (providerId= ${providerId})",
    cause = cause,
)
