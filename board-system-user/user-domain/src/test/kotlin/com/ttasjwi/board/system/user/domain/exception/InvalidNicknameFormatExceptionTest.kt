package com.ttasjwi.board.system.user.domain.exception

import com.ttasjwi.board.system.common.exception.ErrorStatus
import com.ttasjwi.board.system.user.domain.policy.NicknamePolicyImpl
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("InvalidNicknameFormatException: 닉네임 문자열의 포맷이 유효하지 않을 때 발생하는 예외이다.")
class InvalidNicknameFormatExceptionTest {

    @Test
    @DisplayName("예외 기본 속성 테스트")
    fun test() {
        val nickname = "1!@gma"
        val exception = InvalidNicknameFormatException(nickname)

        assertThat(exception.status).isEqualTo(ErrorStatus.BAD_REQUEST)
        assertThat(exception.code).isEqualTo("Error.InvalidNicknameFormat")
        assertThat(exception.args).containsExactly(NicknamePolicyImpl.MIN_LENGTH, NicknamePolicyImpl.MAX_LENGTH, nickname)
        assertThat(exception.source).isEqualTo("nickname")
        assertThat(exception.message).isEqualTo(
            "닉네임은 ${NicknamePolicyImpl.MIN_LENGTH}자 이상, " +
                    "${NicknamePolicyImpl.MAX_LENGTH}자 이하의 한글/영어/숫자로만 구성되어야 합니다. " +
                    "(nickname= $nickname)"
        )
    }
}
