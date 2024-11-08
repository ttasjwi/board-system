package com.ttasjwi.board.system.core.exception.api

import com.ttasjwi.board.system.core.api.ErrorResponse
import com.ttasjwi.board.system.core.exception.CustomException
import com.ttasjwi.board.system.core.exception.ErrorStatus
import com.ttasjwi.board.system.core.exception.ValidationExceptionCollector
import com.ttasjwi.board.system.core.locale.LocaleManager
import com.ttasjwi.board.system.core.message.MessageResolver
import com.ttasjwi.board.system.logging.getLogger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
internal class GlobalExceptionController(
    private val messageResolver: MessageResolver,
    private val localeManager: LocaleManager,
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
        val cause = e.cause
        if (cause is NotImplementedError) {
            return handleNotImplementedError(cause)
        }
        log.error(e)

        return makeSingleErrorResponse(
            errorStatus = ErrorStatus.APPLICATION_ERROR,
            errorItem = makeErrorItem("Error.Server", emptyList(), "server"),
        )
    }

    /**
     * 커스텀 예외를 처리합니다.
     */
    @ExceptionHandler(CustomException::class)
    fun handleCustomException(e: CustomException): ResponseEntity<ErrorResponse> {
        return makeSingleErrorResponse(
            errorStatus = e.status,
            errorItem = makeErrorItem(code = e.code, args = e.args, source = e.source)
        )
    }

    /**
     * 검증 예외 수집기에 수집된 예외들을 모아서 하나의 응답으로 내립니다.
     */
    @ExceptionHandler(ValidationExceptionCollector::class)
    fun handleValidationExceptionCollector(exceptionCollector: ValidationExceptionCollector): ResponseEntity<ErrorResponse> {
        return makeMultipleErrorResponse(
            errorStatus = ErrorStatus.INVALID_ARGUMENT,
            errorItems = makeErrorItems(exceptionCollector.getExceptions())
        )
    }

    private fun handleNotImplementedError(e: NotImplementedError): ResponseEntity<ErrorResponse> {
        log.error(e)

        return makeSingleErrorResponse(
            errorStatus = ErrorStatus.NOT_IMPLEMENTED,
            errorItem = makeErrorItem(code = "Error.NotImplemented", source = "server")
        )
    }


    private fun makeSingleErrorResponse(
        errorStatus: ErrorStatus,
        errorItem: ErrorResponse.ErrorItem,
    ): ResponseEntity<ErrorResponse> {
        return makeMultipleErrorResponse(errorStatus, listOf(errorItem))
    }

    private fun makeMultipleErrorResponse(
        errorStatus: ErrorStatus,
        errorItems: List<ErrorResponse.ErrorItem>
    ): ResponseEntity<ErrorResponse> {
        val commonCode = "Error.Occurred"
        val locale = localeManager.getCurrentLocale()
        return ResponseEntity
            .status(resolveHttpStatus(errorStatus))
            .body(
                ErrorResponse(
                    code = commonCode,
                    message = messageResolver.resolve("$commonCode.message", locale),
                    description = messageResolver.resolve("$commonCode.description", locale),
                    errors = errorItems
                )
            )
    }

    private fun makeErrorItem(
        code: String,
        args: List<Any?> = emptyList(),
        source: String,
    ): ErrorResponse.ErrorItem {
        val locale = localeManager.getCurrentLocale()
        return ErrorResponse.ErrorItem(
            code = code,
            message = messageResolver.resolve("$code.message", locale),
            description = messageResolver.resolve("$code.description", locale, args),
            source = source
        )
    }

    private fun makeErrorItems(
        exceptions: List<CustomException>
    ): List<ErrorResponse.ErrorItem> {
        return exceptions.map {
            makeErrorItem(
                code = it.code,
                args = it.args,
                source = it.source,
            )
        }
    }
}
