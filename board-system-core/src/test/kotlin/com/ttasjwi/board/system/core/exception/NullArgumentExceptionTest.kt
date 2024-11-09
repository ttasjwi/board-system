package com.ttasjwi.board.system.core.exception

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("NullArgumentException: Null 이여서 안 되는 상황, 즉 어떤 필드값이 필수여야함을 나타내는 예외")
class NullArgumentExceptionTest {

    @Test
    @DisplayName("예외 기본값 테스트")
    fun test() {
        // given
        val source = "username"

        // when
        val exception = NullArgumentException(source)

        // then
        assertThat(exception.status).isEqualTo(ErrorStatus.BAD_REQUEST)
        assertThat(exception.code).isEqualTo("Error.NullArgument")
        assertThat(exception.args).containsExactly(source)
        assertThat(exception.source).isEqualTo(source)
        assertThat(exception.debugMessage).isEqualTo("${source}은(는) 필수입니다.")
        assertThat(exception.cause).isNull()
    }
}
