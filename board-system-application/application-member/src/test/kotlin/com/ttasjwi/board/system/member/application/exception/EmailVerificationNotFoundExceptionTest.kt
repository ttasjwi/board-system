package com.ttasjwi.board.system.member.application.exception

import com.ttasjwi.board.system.core.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("EmailVerificationNotFoundException: 이메일 인증 조회 실패(만료됐거나, 없거나) 시, 발생하는 예외")
class EmailVerificationNotFoundExceptionTest {

    @Test
    @DisplayName("예외 기본값 테스트")
    fun test() {
        val email = "hello@gmail.com"
        val exception = EmailVerificationNotFoundException(email)

        assertThat(exception.status).isEqualTo(ErrorStatus.BAD_REQUEST)
        assertThat(exception.code).isEqualTo("Error.EmailVerificationNotFound")
        assertThat(exception.source).isEqualTo("emailVerification")
        assertThat(exception.args).containsExactly(email)
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("이메일 인증이 만료됐거나 인증이 존재하지 않습니다. 다시 인증 절차를 시작해주세요. (email=${email})")
    }
}
