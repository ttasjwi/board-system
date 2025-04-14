package com.ttasjwi.board.system.common.auth

import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("RefreshTokenExpiredException: 리프레시토큰이 만료됐을 때 발생하는 예외")
class RefreshTokenExpiredExceptionTest {

    @Test
    @DisplayName("예외 기본값 테스트")
    fun test() {
        val debugMessage = "리프레시 토큰 만료(픽스쳐 메시지)"
        val exception = RefreshTokenExpiredException(debugMessage)

        assertThat(exception.status).isEqualTo(ErrorStatus.UNAUTHENTICATED)
        assertThat(exception.code).isEqualTo("Error.RefreshTokenExpired")
        assertThat(exception.source).isEqualTo("refreshToken")
        assertThat(exception.args).isEmpty()
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo(debugMessage)
    }
}
