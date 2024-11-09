package com.ttasjwi.board.system.member.domain.exception

import com.ttasjwi.board.system.core.exception.ErrorStatus
import com.ttasjwi.board.system.core.time.fixture.timeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("EmailVerificationExpiredException: 이메일 인증이 만료(코드 만료, 인증 만료)됐을 경우 발생하는 예외")
class EmailVerificationExpiredExceptionTest {

    @Test
    @DisplayName("예외 기본값 테스트")
    fun test() {
        val email = "hello@gmail.com"
        val expiredAt = timeFixture(minute = 5)
        val currentTime = timeFixture(minute = 8)

        val exception = EmailVerificationExpiredException(email, expiredAt, currentTime)

        assertThat(exception.status).isEqualTo(ErrorStatus.BAD_REQUEST)
        assertThat(exception.code).isEqualTo("Error.EmailVerificationExpired")
        assertThat(exception.source).isEqualTo("emailVerification")
        assertThat(exception.args).containsExactly(email, expiredAt, currentTime)
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("이메일 인증이 만료됐습니다. 다시 인증해주세요. (email=$email, expiredAt=$expiredAt, currentTime=$currentTime)")
    }
}
