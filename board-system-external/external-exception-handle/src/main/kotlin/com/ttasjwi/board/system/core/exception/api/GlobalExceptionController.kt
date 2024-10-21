package com.ttasjwi.board.system.core.exception.api

import com.ttasjwi.board.system.core.api.ErrorResponse
import com.ttasjwi.board.system.core.exception.CustomException
import com.ttasjwi.board.system.core.exception.ValidationExceptionCollector
import com.ttasjwi.board.system.core.message.MessageResolver
import com.ttasjwi.board.system.logging.getLogger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
internal class GlobalExceptionController(
    private val messageResolver: MessageResolver
) {

    companion object {
        private val log = getLogger(GlobalExceptionController::class.java)
    }

    /**
     * 커스텀 예외가 아닌 모든 예외들은 기본적으로 여기로 오게 됩니다.
     * 커스텀 예외가 아닌 예외들은 커스텀 예외로 감싸거나 다른 ExceptionHandler 를 통해 처리할 수 있도록 만들어야 합니다.
     * 여기로 온 예외는 모두 500 예외로 나가게 됩니다.
     */
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        val code = "Error.Server"
        val httpStatus = HttpStatus.INTERNAL_SERVER_ERROR

        log.error(e)

        return ResponseEntity
            .status(httpStatus)
            .body(
                makeErrorResponse(
                    listOf(
                        ErrorResponse.ErrorItem(
                            code = code,
                            message = messageResolver.resolveMessage(code),
                            description = messageResolver.resolveDescription(code),
                            source = "server"
                        )
                    )
                )
            )
    }

    /**
     * 커스텀 예외를 처리합니다.
     */
    @ExceptionHandler(CustomException::class)
    fun handleCustomException(e: CustomException): ResponseEntity<ErrorResponse> {
        val code = e.code
        val httpStatus = resolveHttpStatus(e.status)

        return ResponseEntity
            .status(httpStatus)
            .body(
                makeErrorResponse(
                    listOf(
                        ErrorResponse.ErrorItem(
                            code = code,
                            message = messageResolver.resolveMessage(code),
                            description = messageResolver.resolveDescription(code, e.args),
                            source = e.source
                        )
                    )
                )
            )
    }

    /**
     * 검증 예외 수집기에 수집된 예외들을 모아서 하나의 응답으로 내립니다.
     */
    @ExceptionHandler(ValidationExceptionCollector::class)
    fun handleValidationExceptionCollector(exceptionCollector: ValidationExceptionCollector): ResponseEntity<ErrorResponse> {
        val httpStatus = resolveHttpStatus(exceptionCollector.status)
        return ResponseEntity
            .status(httpStatus)
            .body(
                makeErrorResponse(
                    exceptionCollector.getExceptions().map {
                        ErrorResponse.ErrorItem(
                            code = it.code,
                            message = messageResolver.resolveMessage(it.code),
                            description = messageResolver.resolveDescription(it.code, it.args),
                            source = it.source
                        )
                    }
                )
            )
    }

    private fun makeErrorResponse(
        errors: List<ErrorResponse.ErrorItem>
    ): ErrorResponse {

        val commonCode = "Error.Occurred"
        return ErrorResponse(
            code = commonCode,
            message = messageResolver.resolveMessage(commonCode),
            description = messageResolver.resolveDescription(commonCode),
            errors = errors
        )
    }
}
