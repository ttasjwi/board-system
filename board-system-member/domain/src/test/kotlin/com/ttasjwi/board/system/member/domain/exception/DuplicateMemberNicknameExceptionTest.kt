package com.ttasjwi.board.system.member.domain.exception

import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("DuplicateMemberNicknameException: 닉네임이 중복될 때 발생하는 예외")
class DuplicateMemberNicknameExceptionTest {

    @Test
    @DisplayName("예외 기본값 테스트")
    fun test() {
        val nickname = "hello"
        val exception = DuplicateMemberNicknameException(nickname)

        assertThat(exception.status).isEqualTo(ErrorStatus.CONFLICT)
        assertThat(exception.code).isEqualTo("Error.DuplicateMemberNickname")
        assertThat(exception.source).isEqualTo("nickname")
        assertThat(exception.args).containsExactly(nickname)
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("중복되는 닉네임의 회원이 존재합니다.(nickname=${nickname})")
    }
}
