package com.ttasjwi.board.system.member.domain.exception

import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("InvalidEmailVerificationCodeException: 이메일 인증 코드가 잘못됐을 때 발생하는 예외")
class InvalidEmailVerificationCodeExceptionTest {

    @Test
    @DisplayName("예외 기본값 테스트")
    fun test() {
        val exception = InvalidEmailVerificationCodeException()

        assertThat(exception.status).isEqualTo(ErrorStatus.BAD_REQUEST)
        assertThat(exception.code).isEqualTo("Error.InvalidEmailVerificationCode")
        assertThat(exception.source).isEqualTo("code")
        assertThat(exception.args).isEmpty()
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("이메일 인증 코드가 올바르지 않습니다.")
    }
}
