package com.ttasjwi.board.system.member.domain.exception

import com.ttasjwi.board.system.global.exception.ErrorStatus
import com.ttasjwi.board.system.member.domain.service.impl.UsernameManagerImpl
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("InvalidUsernameFormatException: 사용자 아이디 문자열의 포맷이 유효하지 않을 때 발생하는 예외이다.")
class InvalidUsernameFormatExceptionTest {

    @Test
    @DisplayName("예외 기본 속성 테스트")
    fun test() {
        val usernameValue = "1!@gma"
        val exception = InvalidUsernameFormatException(usernameValue)

        assertThat(exception.status).isEqualTo(ErrorStatus.BAD_REQUEST)
        assertThat(exception.code).isEqualTo("Error.InvalidUsernameFormat")
        assertThat(exception.args).containsExactly(UsernameManagerImpl.MIN_LENGTH, UsernameManagerImpl.MAX_LENGTH, usernameValue)
        assertThat(exception.source).isEqualTo("username")
        assertThat(exception.message).isEqualTo(
            "사용자아이디(username)는 ${UsernameManagerImpl.MIN_LENGTH} 자 이상 " +
                    "${UsernameManagerImpl.MAX_LENGTH} 이하여야 하며, 영어소문자/숫자/언더바 만 허용됩니다. " +
                    "(username = $usernameValue)"
        )
    }
}
