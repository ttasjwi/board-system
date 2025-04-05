package com.ttasjwi.board.system.spring.security.exception

import com.ttasjwi.board.system.global.exception.ErrorStatus
import com.ttasjwi.board.system.global.springsecurity.oauth2.exception.InvalidOAuth2ProviderIdException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("InvalidOAuth2ProviderIdException: 사용자의 소셜로그인 요청 시 providerId 가 우리 서비스에저 지원되지 않을 때")
class InvalidOAuth2ProviderIdExceptionTest {

    @Test
    @DisplayName("예외 기본값 테스트")
    fun test() {
        val providerId = "fire"
        val cause = IllegalArgumentException("Invalid Client Registration with Id: fire")
        val exception = InvalidOAuth2ProviderIdException(
            providerId = providerId,
            cause = cause
        )

        assertThat(exception.status).isEqualTo(ErrorStatus.BAD_REQUEST)
        assertThat(exception.code).isEqualTo("Error.InvalidOAuth2ProviderId")
        assertThat(exception.source).isEqualTo("providerId")
        assertThat(exception.args).containsExactly(providerId)
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isEqualTo(cause)
        assertThat(exception.debugMessage).isEqualTo("우리 서비스에서 연동하는 소셜서비스 제공자 id가 아닙니다. (providerId= ${providerId})")
    }
}
