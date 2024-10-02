package com.ttasjwi.board.system.core.exception

import com.ttasjwi.board.system.core.exception.fixture.customExceptionFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("CustomException: 우리 서비스의 커스텀 예외를 표준화한 추상 예외")
class CustomExceptionTest {

    @Test
    @DisplayName("커스텀 예외는 생성시 전달한 값을 가지고 있다.")
    fun test1() {
        // given
        val status = ErrorStatus.INVALID_ARGUMENT
        val code = "Error.SomeError"
        val args = listOf("someArgs1", "someArgs2")
        val source = "someSource"
        val debugMessage = "Some debug Message with ${args[0]}, ${args[1]}"

        // when
        val exception = customExceptionFixture(
            status= status,
            code = code,
            args = args,
            source = source,
            debugMessage = debugMessage,
        )

        // then
        assertThat(exception.status).isEqualTo(status)
        assertThat(exception.code).isEqualTo(code)
        assertThat(exception.args).isEqualTo(args)
        assertThat(exception.source).isEqualTo(source)
        assertThat(exception.debugMessage).isEqualTo(debugMessage)
        assertThat(exception.message).isEqualTo(debugMessage)
        assertThat(exception.cause).isNull()
    }

    @Test
    @DisplayName("커스텀 예외에 근원 예외를 함께 더하여 생성시 근원 예외 정보를 가지고 있다.")
    fun test2() {
        // given
        val cause = IllegalArgumentException("origin cause")

        // when
        val exception = customExceptionFixture(
            cause = cause
        )

        // then
        assertThat(exception.cause).isEqualTo(cause)
    }

}
