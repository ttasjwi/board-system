package com.ttasjwi.board.system.core.exception.fixture

import com.ttasjwi.board.system.core.exception.CustomException
import com.ttasjwi.board.system.core.exception.ErrorStatus

fun customExceptionFixture(
    status: ErrorStatus = ErrorStatus.BAD_REQUEST,
    code: String = "Error.SomeThingWrong",
    args: List<Any?> = emptyList(),
    source: String = "someSource",
    debugMessage: String = "테스트 픽스쳐 에러 메시지",
    cause: Throwable? = null
): CustomException {
    return TestCustomException(
        status = status,
        code = code,
        source = source,
        args = args,
        debugMessage = debugMessage,
        cause = cause
    )
}


internal class TestCustomException(
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
