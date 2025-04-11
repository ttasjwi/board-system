package com.ttasjwi.board.system.auth.domain.exception

import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("InvalidRefreshTokenFormatException 테스트")
class InvalidRefreshTokenFormatExceptionTest {

    @Test
    @DisplayName("예외 기본값 테스트")
    fun test1() {
        val exception = InvalidRefreshTokenFormatException()

        assertThat(exception.status).isEqualTo(ErrorStatus.UNAUTHENTICATED)
        assertThat(exception.code).isEqualTo("Error.InvalidRefreshTokenFormat")
        assertThat(exception.source).isEqualTo("refreshToken")
        assertThat(exception.args).isEmpty()
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("리프레시 토큰 포맷이 유효하지 않습니다. 토큰값이 잘못됐거나, 리프레시 토큰이 아닙니다.")
    }

    @Test
    @DisplayName("근원 예외 테스트")
    fun test2() {
        val cause = IllegalArgumentException()
        val exception = InvalidRefreshTokenFormatException(cause)

        assertThat(exception.status).isEqualTo(ErrorStatus.UNAUTHENTICATED)
        assertThat(exception.code).isEqualTo("Error.InvalidRefreshTokenFormat")
        assertThat(exception.source).isEqualTo("refreshToken")
        assertThat(exception.args).isEmpty()
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isEqualTo(cause)
        assertThat(exception.debugMessage).isEqualTo("리프레시 토큰 포맷이 유효하지 않습니다. 토큰값이 잘못됐거나, 리프레시 토큰이 아닙니다.")
    }
}
