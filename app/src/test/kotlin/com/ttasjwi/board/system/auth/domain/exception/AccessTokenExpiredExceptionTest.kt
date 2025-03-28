package com.ttasjwi.board.system.auth.domain.exception

import com.ttasjwi.board.system.common.exception.ErrorStatus
import com.ttasjwi.board.system.common.time.fixture.timeFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("AccessTokenExpiredException: 액세스토큰이 만료됐을 때 발생하는 예외")
class AccessTokenExpiredExceptionTest {

    @Test
    @DisplayName("예외 기본값 테스트")
    fun test1() {
        val expiredAt = timeFixture(minute = 3)
        val currentTime = timeFixture(minute = 5)
        val exception = AccessTokenExpiredException(expiredAt, currentTime)

        assertThat(exception.status).isEqualTo(ErrorStatus.UNAUTHENTICATED)
        assertThat(exception.code).isEqualTo("Error.AccessTokenExpired")
        assertThat(exception.source).isEqualTo("accessToken")
        assertThat(exception.args).containsExactly(expiredAt, currentTime)
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("액세스 토큰의 유효시간이 경과되어 더 이상 유효하지 않습니다. (expiredAt=${expiredAt},currentTime=${currentTime}) 리프레시 토큰을 통해 갱신해주세요. 리프레시 토큰도 만료됐다면 로그인을 다시 하셔야합니다.")
    }
}
