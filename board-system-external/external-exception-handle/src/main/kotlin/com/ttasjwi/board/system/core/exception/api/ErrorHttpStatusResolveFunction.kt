package com.ttasjwi.board.system.core.exception.api

import com.ttasjwi.board.system.core.exception.ErrorStatus
import org.springframework.http.HttpStatus

internal fun resolveHttpStatus(status: ErrorStatus): HttpStatus {
    return when (status) {
        ErrorStatus.INVALID_ARGUMENT -> HttpStatus.BAD_REQUEST
        ErrorStatus.NOT_FOUND -> HttpStatus.NOT_FOUND
        ErrorStatus.UNAUTHENTICATED -> HttpStatus.UNAUTHORIZED
        ErrorStatus.FORBIDDEN -> HttpStatus.FORBIDDEN
        ErrorStatus.CONFLICT -> HttpStatus.CONFLICT
        ErrorStatus.APPLICATION_ERROR -> HttpStatus.INTERNAL_SERVER_ERROR
    }
}