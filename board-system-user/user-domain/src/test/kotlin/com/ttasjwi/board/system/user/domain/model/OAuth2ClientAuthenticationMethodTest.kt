package com.ttasjwi.board.system.user.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("[user-domain] OAuth2ClientAuthenticationMethod 테스트")
class OAuth2ClientAuthenticationMethodTest {

    @Nested
    @DisplayName("restore: 값으로 부터 복원")
    inner class RestoreTest {

        @Test
        @DisplayName("client_secret_basic 테스트")
        fun test1() {
            // given
            val value = "client_secret_basic"

            // when
            val oAuth2ClientAuthenticationMethod = OAuth2ClientAuthenticationMethod.restore(value)

            // then
            assertThat(oAuth2ClientAuthenticationMethod).isEqualTo(OAuth2ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
        }


        @Test
        @DisplayName("대소문자 구분 없이 복원 테스트")
        fun test2() {
            // given
            val value = "CLIENT_SECRET_Basic"

            // when
            val oAuth2ClientAuthenticationMethod = OAuth2ClientAuthenticationMethod.restore(value)

            // then
            assertThat(oAuth2ClientAuthenticationMethod).isEqualTo(OAuth2ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
        }


        @Test
        @DisplayName("client_secret_post 테스트")
        fun test3() {
            // given
            val value = "client_secret_post"

            // when
            val oAuth2ClientAuthenticationMethod = OAuth2ClientAuthenticationMethod.restore(value)

            // then
            assertThat(oAuth2ClientAuthenticationMethod).isEqualTo(OAuth2ClientAuthenticationMethod.CLIENT_SECRET_POST)
        }


        @Test
        @DisplayName("header 테스트")
        fun test4() {
            // given
            val value = "header"

            // when
            val oAuth2ClientAuthenticationMethod = OAuth2ClientAuthenticationMethod.restore(value)

            // then
            assertThat(oAuth2ClientAuthenticationMethod).isEqualTo(OAuth2ClientAuthenticationMethod.HEADER)
        }

        @Test
        @DisplayName("없는 값으로 복원 시도하면 예외 발생")
        fun test5() {
            // given
            val value = "adfadfadf"

            // when
            assertThrows<NoSuchElementException> {
                OAuth2ClientAuthenticationMethod.restore(value)
            }
        }
    }
}
