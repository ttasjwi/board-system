package com.ttasjwi.board.system.auth.domain.exception

import com.ttasjwi.board.system.global.exception.ErrorStatus
import com.ttasjwi.board.system.global.exception.fixture.customExceptionFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("AccessDeniedException: 인가 실패, 권한부족 상황에서 발생하는 예외")
class AccessDeniedExceptionTest {

    @Test
    @DisplayName("예외 기본값 테스트")
    fun test1() {
        val exception = AccessDeniedException()

        assertThat(exception.status).isEqualTo(ErrorStatus.FORBIDDEN)
        assertThat(exception.code).isEqualTo("Error.AccessDenied")
        assertThat(exception.source).isEqualTo("credentials")
        assertThat(exception.args).isEmpty()
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("리소스에 접근할 수 없습니다. 해당 리소스에 접근할 권한이 없습니다.")
    }

    @Test
    @DisplayName("근원 예외를 전달하여 생성할 수 있다.")
    fun test2() {
        val cause = customExceptionFixture()
        val exception = AccessDeniedException(cause)

        assertThat(exception.status).isEqualTo(ErrorStatus.FORBIDDEN)
        assertThat(exception.code).isEqualTo("Error.AccessDenied")
        assertThat(exception.source).isEqualTo("credentials")
        assertThat(exception.args).isEmpty()
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isEqualTo(cause)
        assertThat(exception.debugMessage).isEqualTo("리소스에 접근할 수 없습니다. 해당 리소스에 접근할 권한이 없습니다.")
    }
}
