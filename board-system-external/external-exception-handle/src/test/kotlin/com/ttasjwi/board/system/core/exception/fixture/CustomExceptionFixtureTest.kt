package com.ttasjwi.board.system.core.exception.fixture

import com.ttasjwi.board.system.core.exception.CustomException
import com.ttasjwi.board.system.core.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("CustomExceptionFixture 테스트")
class CustomExceptionFixtureTest {

    @Test
    @DisplayName("필드 값을 전달하지 않아도 기본적인 예외 인스턴스가 생성된다.")
    fun test() {
        // given
        // when
        val exception = customExceptionFixture()

        assertThat(exception).isNotNull
        assertThat(exception).isInstanceOf(CustomException::class.java)
        assertThat(exception.status).isNotNull
        assertThat(exception.code).isNotNull
        assertThat(exception.args).isNotNull
        assertThat(exception.source).isNotNull
        assertThat(exception.debugMessage).isNotNull()
        assertThat(exception.cause).isNull()
    }

    @Test
    @DisplayName("커스텀하게 필드를 지정해서 전달하여 인스턴스를 생성할 수 있다.")
    fun testCustom() {
        // given
        val status = ErrorStatus.INVALID_ARGUMENT
        val code = "Error.InvalidArgumentFixture"
        val args = listOf(1, 2)
        val source = "nickname"
        val debugMessage = "some debug message fixture"
        val cause = IllegalArgumentException("some thing is wrong")

        // when
        val exception = customExceptionFixture(
            status = status,
            code = code,
            args = args,
            source = source,
            debugMessage = debugMessage,
            cause = cause
        )

        assertThat(exception).isNotNull
        assertThat(exception).isInstanceOf(CustomException::class.java)
        assertThat(exception.status).isEqualTo(status)
        assertThat(exception.code).isEqualTo(code)
        assertThat(exception.args).containsExactlyElementsOf(args)
        assertThat(exception.source).isEqualTo(source)
        assertThat(exception.debugMessage).isEqualTo(debugMessage)
        assertThat(exception.cause).isEqualTo(cause)
    }
}
