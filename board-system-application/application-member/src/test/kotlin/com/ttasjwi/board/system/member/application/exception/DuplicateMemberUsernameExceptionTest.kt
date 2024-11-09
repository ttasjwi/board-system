package com.ttasjwi.board.system.member.application.exception

import com.ttasjwi.board.system.core.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("DuplicateMemberUsernameException: 사용자아이디(username)가 중복될 때 발생하는 예외")
class DuplicateMemberUsernameExceptionTest {

    @Test
    @DisplayName("예외 기본값 테스트")
    fun test() {
        val username = "hello"
        val exception = DuplicateMemberUsernameException(username)

        assertThat(exception.status).isEqualTo(ErrorStatus.CONFLICT)
        assertThat(exception.code).isEqualTo("Error.DuplicateMemberUsername")
        assertThat(exception.source).isEqualTo("username")
        assertThat(exception.args).containsExactly(username)
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("중복되는 사용자 아이디(username)의 회원이 존재합니다.(username=${username})")
    }
}
