package com.ttasjwi.board.system.auth.application.exception

import com.ttasjwi.board.system.core.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("LoginFailureException: 로그인 실패 예외")
class LoginFailureExceptionTest {

    @Test
    @DisplayName("예외 테스트")
    fun test() {
        val debugMessage = "로그인 실패 - 픽스쳐 디버깅 메시지"
        val exception = LoginFailureException(debugMessage)

        assertThat(exception.status).isEqualTo(ErrorStatus.UNAUTHENTICATED)
        assertThat(exception.code).isEqualTo("Error.LoginFailure")
        assertThat(exception.args).isEmpty()
        assertThat(exception.source).isEqualTo("credentials")
        assertThat(exception.message).isEqualTo(debugMessage)
        assertThat(exception.debugMessage).isEqualTo(debugMessage)
    }
}
