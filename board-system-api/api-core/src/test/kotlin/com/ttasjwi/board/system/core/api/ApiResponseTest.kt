package com.ttasjwi.board.system.core.api

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("ApiResponse 테스트")
class ApiResponseTest {

    private class SampleSuccessResponse (
        val example: String
    )

    @Test
    @DisplayName("SuccessResponse 의 isSuccess 값은 true 이다.")
    fun successResponseTest() {
        val response = SuccessResponse(
            code = "code",
            message = "message",
            description = "description",
            data = SampleSuccessResponse(
                example = "example"
            )
        )
        assertThat(response.isSuccess).isTrue()
        assertThat(response.code).isEqualTo("code")
        assertThat(response.message).isEqualTo("message")
        assertThat(response.description).isEqualTo("description")
        assertThat(response.data.example).isEqualTo("example")
    }


    @Test
    @DisplayName("ErrorResponse 의 isSuccess 값은 false 이다.")
    fun errorResponseTest() {
        val response = ErrorResponse(
            code = "code",
            message = "message",
            description = "description",
            errors = listOf(
                ErrorResponse.ErrorItem(
                    code = "ErrorCode",
                    message = "ErrorMessage",
                    description = "ErrorDescription",
                    source = "something"
                )
            )
        )
        assertThat(response.isSuccess).isFalse
        assertThat(response.code).isEqualTo("code")
        assertThat(response.message).isEqualTo("message")
        assertThat(response.description).isEqualTo("description")
        assertThat(response.errors[0].code).isEqualTo("ErrorCode")
        assertThat(response.errors[0].message).isEqualTo("ErrorMessage")
        assertThat(response.errors[0].description).isEqualTo("ErrorDescription")
        assertThat(response.errors[0].source).isEqualTo("something")
    }
}
