package com.ttasjwi.board.system.user.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[user-domain] OAuth2UserPrincipal 테스트")
class OAuth2UserPrincipalTest {

    @Test
    @DisplayName("기본 값 테스트")
    fun test() {
        val socialServiceName = "naver"
        val socialServiceUserId = "adfadfadfad"
        val email = "hello@naver.com"

        val oAuth2UserPrincipal = OAuth2UserPrincipal(
            socialServiceName = socialServiceName,
            socialServiceUserId = socialServiceUserId,
            email = email,
        )

        assertThat(oAuth2UserPrincipal.socialServiceName).isEqualTo(socialServiceName)
        assertThat(oAuth2UserPrincipal.socialServiceUserId).isEqualTo(socialServiceUserId)
        assertThat(oAuth2UserPrincipal.email).isEqualTo(email)
    }
}
