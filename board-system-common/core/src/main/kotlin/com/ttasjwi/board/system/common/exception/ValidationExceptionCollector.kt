package com.ttasjwi.board.system.common.exception

/**
 * 여러 개의 입력값 검증예외를 군집으로 모아 관리하리하기 위한 목적으로 정의한 특수 예외
 */
class ValidationExceptionCollector : CustomException(
    status = ErrorStatus.BAD_REQUEST,
    code = "Error.InvalidArguments",
    source = "*",
    args = emptyList(),
    debugMessage = "입력값 유효성 검증에 실패했습니다.",
) {

    private val _exceptions: MutableList<CustomException> = mutableListOf()

    /**
     * 예외를 추가합니다. 우리 서비스에서 정의한 커스텀 예외는 수집하고, 알려지지 않은 예외는 그대로 던집니다.
     */
    fun addCustomExceptionOrThrow(e: Throwable) {
        if (e is CustomException) {
            _exceptions.add(e)
            return
        } else {
            throw e
        }
    }

    fun getExceptions(): List<CustomException> = _exceptions.toList()

    fun isNotEmpty(): Boolean {
        return this._exceptions.isNotEmpty()
    }

    fun throwIfNotEmpty() {
        if (this._exceptions.isNotEmpty()) {
            throw this
        }
        return
    }
}
