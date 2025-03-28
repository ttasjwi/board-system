package com.ttasjwi.board.system.common.api

abstract class ApiResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val description: String,
)

class SuccessResponse<T>(
    code: String,
    message: String,
    description: String,
    val data: T
): ApiResponse(
    isSuccess = true,
    code = code,
    message = message,
    description = description
)

class ErrorResponse(
    code: String,
    message: String,
    description: String,
    val errors: List<ErrorItem>
): ApiResponse(false, code, message, description) {

    class ErrorItem (
        val code: String,
        val message: String,
        val description: String,
        val source: String,
    )
}
