package com.ttasjwi.board.system.user.domain.exception

import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("DuplicateUserEmailException: 이메일이 중복될 때 발생하는 예외")
class DuplicateUserEmailExceptionTest {

    @Test
    @DisplayName("예외 기본값 테스트")
    fun test() {
        val email = "hello@gmail.com"
        val exception = DuplicateUserEmailException(email)

        assertThat(exception.status).isEqualTo(ErrorStatus.CONFLICT)
        assertThat(exception.code).isEqualTo("Error.DuplicateUserEmail")
        assertThat(exception.source).isEqualTo("email")
        assertThat(exception.args).containsExactly(email)
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("중복되는 이메일의 사용자가 존재합니다.(email=${email})")
    }
}
