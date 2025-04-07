package com.ttasjwi.board.system.domain.member.exception

import com.ttasjwi.board.system.global.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("InvalidEmailFormatException: 이메일 문자열의 포맷이 유효하지 않을 때 발생하는 예외이다.")
class InvalidEmailFormatExceptionTest {

    @Test
    @DisplayName("예외 기본 속성 테스트")
    fun test() {
        val emailValue = "1!@gmaicom"
        val exception = InvalidEmailFormatException(emailValue)

        assertThat(exception.status).isEqualTo(ErrorStatus.BAD_REQUEST)
        assertThat(exception.code).isEqualTo("Error.InvalidEmailFormat")
        assertThat(exception.args).containsExactly(emailValue)
        assertThat(exception.source).isEqualTo("email")
        assertThat(exception.message).isEqualTo("이메일의 형식이 올바르지 않습니다. (email = $emailValue)")
    }
}
