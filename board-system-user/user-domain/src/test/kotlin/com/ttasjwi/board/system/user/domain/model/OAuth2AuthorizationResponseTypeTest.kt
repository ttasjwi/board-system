package com.ttasjwi.board.system.user.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("[user-domain] OAuth2AuthorizationResponseType 테스트")
class OAuth2AuthorizationResponseTypeTest {

    @Nested
    @DisplayName("restore: 값으로 부터 복원")
    inner class RestoreTest {
        @Test
        @DisplayName("code 복원 테스트")
        fun restoreTest1() {
            val responseTypeString = "code"
            val responseType = OAuth2AuthorizationResponseType.restore(responseTypeString)

            assertThat(responseType).isEqualTo(OAuth2AuthorizationResponseType.CODE)
        }

        @Test
        @DisplayName("code 복원 시, 대소문자 구분을 하지 않는다.")
        fun restoreTest2() {
            val responseTypeString = "CodE"
            val responseType = OAuth2AuthorizationResponseType.restore(responseTypeString)

            assertThat(responseType).isEqualTo(OAuth2AuthorizationResponseType.CODE)
        }


        @Test
        @DisplayName("지원하지 않는 값일 경우 복원할 수 없음")
        fun restoreTest3() {
            // given
            val responseTypeString = "Cod!E"

            // when
            // then
            assertThrows<NoSuchElementException> {
                OAuth2AuthorizationResponseType.restore(responseTypeString)
            }
        }
    }
}
