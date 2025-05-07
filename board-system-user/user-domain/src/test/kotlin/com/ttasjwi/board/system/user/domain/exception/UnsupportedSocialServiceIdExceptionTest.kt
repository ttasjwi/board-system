package com.ttasjwi.board.system.user.domain.exception

import com.ttasjwi.board.system.common.exception.ErrorStatus
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[user-domain] UnsupportedSocialServiceIdException: 사용자의 소셜로그인 요청 시 socialServiceId 가 우리 서비스에서 지원되지 않을 때")
class UnsupportedSocialServiceIdExceptionTest {

    @Test
    @DisplayName("예외 기본값 테스트")
    fun test() {
        val socialServiceId = "fire"
        val exception = UnsupportedSocialServiceIdException(
            socialServiceId = socialServiceId,
        )

        assertThat(exception.status).isEqualTo(ErrorStatus.BAD_REQUEST)
        assertThat(exception.code).isEqualTo("Error.UnsupportedSocialServiceId")
        assertThat(exception.source).isEqualTo("socialServiceId")
        assertThat(exception.args).containsExactly(socialServiceId)
        assertThat(exception.message).isEqualTo(exception.debugMessage)
        assertThat(exception.cause).isNull()
        assertThat(exception.debugMessage).isEqualTo("우리 서비스에서 연동하는 소셜서비스 id가 아닙니다. (socialServiceId= ${socialServiceId})")
    }
}
