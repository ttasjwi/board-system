package com.ttasjwi.board.system.global.error

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

class ErrorResponseElement(
    val code: String,
    val message: String,
    val description: String,
    val source: String,
)
