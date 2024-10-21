package com.ttasjwi.board.system.core.exception.fixture

import com.ttasjwi.board.system.core.exception.CustomException
import com.ttasjwi.board.system.core.exception.ErrorStatus

fun customExceptionFixture(
    status: ErrorStatus = ErrorStatus.INVALID_ARGUMENT,
    code: String = "Error.Fixture",
    args: List<Any?> = emptyList(),
    source: String = "fixtureErrorSource",
    debugMessage: String = "커스텀 예외 픽스쳐입니다.",
    cause: Throwable? = null,
): CustomException {
    return CustomExceptionFixture(
        status = status,
        code = code,
        args = args,
        source = source,
        debugMessage = debugMessage,
        cause = cause
    )
}

internal class CustomExceptionFixture(
    status: ErrorStatus,
    code: String,
    args: List<Any?>,
    source: String,
    debugMessage: String,
    cause: Throwable?
) : CustomException(
    status = status,
    code = code,
    args = args,
    source = source,
    debugMessage = debugMessage,
    cause = cause
)
