package com.ttasjwi.board.system.auth.domain.exception

import com.ttasjwi.board.system.global.exception.ErrorStatus
import com.ttasjwi.board.system.global.exception.fixture.customExceptionFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("UnauthenticatedException: 인증이 필요할 때 발생하는 예외")
class UnauthenticatedExceptionTest {

    @Test
    @DisplayName("예외 기본값 테스트")
    fun test1() {
        val exception = UnauthenticatedException()

        assertThat(exception.status).isEqualTo(ErrorStatus.UNAUTHENTICATED)
        assertThat(exception.code).isEqualTo("Error.Unauthenticated")
        assertThat(exception.source).isEqualTo("credentials")
        assertThat(exception.args).isEmpty()
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("리소스에 접근할 수 없습니다. 이 리소스에 접근하기 위해서는 인증이 필요합니다.")
    }

    @Test
    @DisplayName("근원 예외를 전달하여 생성할 수 있다.")
    fun test2() {
        val cause = customExceptionFixture()
        val exception = UnauthenticatedException(cause)

        assertThat(exception.status).isEqualTo(ErrorStatus.UNAUTHENTICATED)
        assertThat(exception.code).isEqualTo("Error.Unauthenticated")
        assertThat(exception.source).isEqualTo("credentials")
        assertThat(exception.args).isEmpty()
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isEqualTo(cause)
        assertThat(exception.debugMessage).isEqualTo("리소스에 접근할 수 없습니다. 이 리소스에 접근하기 위해서는 인증이 필요합니다.")
    }
}
