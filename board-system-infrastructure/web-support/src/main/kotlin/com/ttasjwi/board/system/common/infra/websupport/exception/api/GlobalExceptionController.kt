package com.ttasjwi.board.system.common.infra.websupport.exception.api

import com.ttasjwi.board.system.common.exception.CustomException
import com.ttasjwi.board.system.common.exception.ErrorStatus
import com.ttasjwi.board.system.common.exception.ValidationExceptionCollector
import com.ttasjwi.board.system.common.locale.LocaleResolver
import com.ttasjwi.board.system.common.logger.getLogger
import com.ttasjwi.board.system.common.message.MessageResolver
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.resource.NoResourceFoundException

@RestControllerAdvice
internal class GlobalExceptionController(
    private val messageResolver: MessageResolver,
    private val localeResolver: LocaleResolver,
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
            errorItem = makeElement("Error.Server", emptyList(), "server"),
        )
    }

    /**
     * 커스텀 예외를 처리합니다.
     */
    @ExceptionHandler(CustomException::class)
    fun handleCustomException(e: CustomException): ResponseEntity<ErrorResponse> {
        return makeSingleErrorResponse(
            errorStatus = e.status,
            errorItem = makeElement(code = e.code, args = e.args, source = e.source)
        )
    }

    /**
     * 검증 예외 수집기에 수집된 예외들을 모아서 하나의 응답으로 내립니다.
     */
    @ExceptionHandler(ValidationExceptionCollector::class)
    fun handleValidationExceptionCollector(exceptionCollector: ValidationExceptionCollector): ResponseEntity<ErrorResponse> {
        return makeMultipleErrorResponse(
            errorStatus = ErrorStatus.BAD_REQUEST,
            errorItems = makeElements(exceptionCollector.getExceptions())
        )
    }

    @ExceptionHandler(NoResourceFoundException::class)
    fun handleNoResourceFoundException(e: NoResourceFoundException): ResponseEntity<ErrorResponse> {
        return makeSingleErrorResponse(
            errorStatus = ErrorStatus.NOT_FOUND,
            errorItem = makeElement(
                code = "Error.ResourceNotFound",
                args = listOf(e.httpMethod.name(), "/${e.resourcePath}"),
                source = "httpMethod,resourcePath",
            )
        )
    }

    private fun handleNotImplementedError(e: NotImplementedError): ResponseEntity<ErrorResponse> {
        log.error(e)

        return makeSingleErrorResponse(
            errorStatus = ErrorStatus.NOT_IMPLEMENTED,
            errorItem = makeElement(code = "Error.NotImplemented", source = "server")
        )
    }

    private fun makeSingleErrorResponse(
        errorStatus: ErrorStatus,
        errorItem: ErrorResponseElement,
    ): ResponseEntity<ErrorResponse> {
        return makeMultipleErrorResponse(errorStatus, listOf(errorItem))
    }

    private fun makeMultipleErrorResponse(
        errorStatus: ErrorStatus,
        errorItems: List<ErrorResponseElement>
    ): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(resolveHttpStatus(errorStatus))
            .body(
                ErrorResponse.of(errorItems)
            )
    }

    private fun makeElement(
        code: String,
        args: List<Any?> = emptyList(),
        source: String,
    ): ErrorResponseElement {
        val locale = localeResolver.getCurrentLocale()
        return ErrorResponseElement(
            code = code,
            message = messageResolver.resolve("$code.message", locale),
            description = messageResolver.resolve("$code.description", locale, args),
            source = source
        )
    }

    private fun makeElements(
        exceptions: List<CustomException>
    ): List<ErrorResponseElement> {
        return exceptions.map {
            makeElement(
                code = it.code,
                args = it.args,
                source = it.source,
            )
        }
    }
}
