package com.ttasjwi.board.system.common.infra.websupport.exception.api

import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.springframework.http.HttpStatus

internal fun resolveHttpStatus(status: ErrorStatus): HttpStatus {
    return when (status) {
        ErrorStatus.BAD_REQUEST -> HttpStatus.BAD_REQUEST
        ErrorStatus.NOT_IMPLEMENTED -> HttpStatus.NOT_IMPLEMENTED
        ErrorStatus.NOT_FOUND -> HttpStatus.NOT_FOUND
        ErrorStatus.UNAUTHENTICATED -> HttpStatus.UNAUTHORIZED
        ErrorStatus.FORBIDDEN -> HttpStatus.FORBIDDEN
        ErrorStatus.CONFLICT -> HttpStatus.CONFLICT
        ErrorStatus.APPLICATION_ERROR -> HttpStatus.INTERNAL_SERVER_ERROR
    }
}
