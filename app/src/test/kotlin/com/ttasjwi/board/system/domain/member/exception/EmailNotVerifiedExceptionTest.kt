package com.ttasjwi.board.system.domain.member.exception

import com.ttasjwi.board.system.global.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("EmailNotVerifiedException: 이메일이 인증되지 않았을 때 발생하는 예외")
class EmailNotVerifiedExceptionTest {

    @Test
    @DisplayName("예외 기본값 테스트")
    fun test() {
        val email = "hello@gmail.com"

        val exception = EmailNotVerifiedException(email)

        assertThat(exception.status).isEqualTo(ErrorStatus.BAD_REQUEST)
        assertThat(exception.code).isEqualTo("Error.EmailNotVerified")
        assertThat(exception.source).isEqualTo("emailVerification")
        assertThat(exception.args).containsExactly(email)
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("이 이메일은 인증되지 않았습니다. 인증을 먼저 수행해주세요. (email=${email})")
    }
}
