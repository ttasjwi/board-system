package com.ttasjwi.board.system.common.infra.websupport.exception.api

data class ErrorResponse
internal constructor(
    val errors: List<ErrorResponseElement>
) {
    companion object {

        fun of(errors: List<ErrorResponseElement>): ErrorResponse {
            return ErrorResponse(errors)
        }
    }
}

data class ErrorResponseElement(
    val code: String,
    val message: String,
    val description: String,
    val source: String,
)
