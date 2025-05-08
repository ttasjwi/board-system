package com.ttasjwi.board.system.user.domain.exception

import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[user-domain] OAuth2AuthorizationRequestNotFoundException: OAuth2 인가요청 조회 실패 예외")
class OAuth2AuthorizationRequestNotFoundExceptionTest {

    @Test
    @DisplayName("예외 테스트")
    fun test() {
        val state = "adfafdadf"
        val exception = OAuth2AuthorizationRequestNotFoundException(state)

        assertThat(exception.status).isEqualTo(ErrorStatus.UNAUTHENTICATED)
        assertThat(exception.code).isEqualTo("Error.OAuth2AuthorizationRequestNotFound")
        assertThat(exception.args).containsExactly(state)
        assertThat(exception.source).isEqualTo("state")
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.debugMessage).isEqualTo("우리 서비스를 통해 소셜 서비스 로그인 요청을 한 내역이 없습니다. (state = $state)")
    }
}
