package com.ttasjwi.board.system.spring.security.exception

import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("InvalidAuthorizationHeaderFormatException: Authorization 헤더값 포맷이 잘못된 형식일 때 발생하는 예외")
class InvalidAuthorizationHeaderFormatExceptionTest {

    @Test
    @DisplayName("예외 기본값 테스트")
    fun test() {
        val exception = InvalidAuthorizationHeaderFormatException()

        assertThat(exception.status).isEqualTo(ErrorStatus.UNAUTHENTICATED)
        assertThat(exception.code).isEqualTo("Error.InvalidAuthorizationHeaderFormat")
        assertThat(exception.source).isEqualTo("authorizationHeader")
        assertThat(exception.args).isEmpty()
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("잘못된 Authorization 헤더 형식입니다. 토큰값을 Authorization 헤더에 'Bearer [토큰값]' 형식으로 보내주세요.")
    }
}
