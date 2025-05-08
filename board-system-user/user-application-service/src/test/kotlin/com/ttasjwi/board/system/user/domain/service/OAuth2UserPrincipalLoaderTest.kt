package com.ttasjwi.board.system.user.domain.service

import com.ttasjwi.board.system.user.domain.model.fixture.kakaoOAuth2AuthorizationRequestFixture
import com.ttasjwi.board.system.user.domain.model.fixture.kakaoOAuth2ClientRegistrationFixgture
import com.ttasjwi.board.system.user.domain.model.fixture.naverOAuth2AuthorizationRequestFixture
import com.ttasjwi.board.system.user.domain.model.fixture.naverOAuth2ClientRegistrationFixture
import com.ttasjwi.board.system.user.domain.port.fixture.OAuth2AccessTokenClientPortFixture
import com.ttasjwi.board.system.user.domain.port.fixture.OAuth2UserPrincipalClientPortFixture
import com.ttasjwi.board.system.user.domain.port.fixture.OidcOAuth2UserPrincipalPortFixture
import com.ttasjwi.board.system.user.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("[user-application-service] OAuth2UserPrincipalLoader 테스트")
class OAuth2UserPrincipalLoaderTest {

    private lateinit var oAuth2UserPrincipalLoader: OAuth2UserPrincipalLoader
    private lateinit var oAuth2AccessTokenClientPortFixture: OAuth2AccessTokenClientPortFixture
    private lateinit var oAuth2UserPrincipalClientPortFixture: OAuth2UserPrincipalClientPortFixture
    private lateinit var oidcOAuth2UserPrincipalClientPortFixture: OidcOAuth2UserPrincipalPortFixture


    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        oAuth2UserPrincipalLoader = container.oAuth2UserPrincipalLoader
        oAuth2AccessTokenClientPortFixture = container.oAuth2AccessTokenClientPortFixture
        oAuth2UserPrincipalClientPortFixture = container.oAuth2UserPrincipalClientPortFixture
        oidcOAuth2UserPrincipalClientPortFixture = container.oidcOAuth2UserPrincipalPortFixture
    }


    @Test
    @DisplayName("OAuth2 성공 테스트")
    fun testOAuth2Success() {
        // given
        val code = "code"
        val oAuth2ClientRegistration = naverOAuth2ClientRegistrationFixture()
        val oAuth2AuthorizationRequest = naverOAuth2AuthorizationRequestFixture()

        val socialServiceName = "naver"
        val socialServiceUserId = "social-service-user-id"
        val email = "hello@naver.com"

        oAuth2AccessTokenClientPortFixture.changeAccessToken("accessToken")
        oAuth2AccessTokenClientPortFixture.changeIdToken(null)

        oAuth2UserPrincipalClientPortFixture.changeSocialServiceName(socialServiceName)
        oAuth2UserPrincipalClientPortFixture.changeSocialServiceUserId(socialServiceUserId)
        oAuth2UserPrincipalClientPortFixture.changeEmail(email)


        // when
        val oAuth2UserPrincipal = oAuth2UserPrincipalLoader.getOAuth2UserPrincipal(
            code = code,
            oAuth2ClientRegistration = oAuth2ClientRegistration,
            oAuth2AuthorizationRequest = oAuth2AuthorizationRequest
        )

        // then
        assertThat(oAuth2UserPrincipal.socialServiceName).isEqualTo(socialServiceName)
        assertThat(oAuth2UserPrincipal.socialServiceUserId).isEqualTo(socialServiceUserId)
        assertThat(oAuth2UserPrincipal.email).isEqualTo(email)
        assertThat(oAuth2UserPrincipalClientPortFixture.callCount).isEqualTo(1)
        assertThat(oidcOAuth2UserPrincipalClientPortFixture.callCount).isEqualTo(0)
    }


    @Test
    @DisplayName("Oidc 성공 테스트")
    fun testOidcSuccess() {
        // given
        val code = "code"
        val oAuth2ClientRegistration = kakaoOAuth2ClientRegistrationFixgture()
        val oAuth2AuthorizationRequest = kakaoOAuth2AuthorizationRequestFixture()

        val socialServiceName = "kakao"
        val socialServiceUserId = "social-service-user-id"
        val email = "hello@kakao.com"

        oAuth2AccessTokenClientPortFixture.changeAccessToken("accessToken")
        oAuth2AccessTokenClientPortFixture.changeIdToken("idToken")

        oidcOAuth2UserPrincipalClientPortFixture.changeSocialServiceName(socialServiceName)
        oidcOAuth2UserPrincipalClientPortFixture.changeSocialServiceUserId(socialServiceUserId)
        oidcOAuth2UserPrincipalClientPortFixture.changeEmail(email)

        // when
        val oAuth2UserPrincipal = oAuth2UserPrincipalLoader.getOAuth2UserPrincipal(
            code = code,
            oAuth2ClientRegistration = oAuth2ClientRegistration,
            oAuth2AuthorizationRequest = oAuth2AuthorizationRequest
        )

        // then
        assertThat(oAuth2UserPrincipal.socialServiceName).isEqualTo(socialServiceName)
        assertThat(oAuth2UserPrincipal.socialServiceUserId).isEqualTo(socialServiceUserId)
        assertThat(oAuth2UserPrincipal.email).isEqualTo(email)
        assertThat(oAuth2UserPrincipalClientPortFixture.callCount).isEqualTo(0)
        assertThat(oidcOAuth2UserPrincipalClientPortFixture.callCount).isEqualTo(1)
    }


    @Test
    @DisplayName("Oidc 방식인데, 액세스토큰 응답에 id 토큰이 없을 경우 예외 발생")
    fun testOidcIdTokenNotFound() {
        // given
        val code = "code"
        val oAuth2ClientRegistration = kakaoOAuth2ClientRegistrationFixgture()
        val oAuth2AuthorizationRequest = kakaoOAuth2AuthorizationRequestFixture()

        oAuth2AccessTokenClientPortFixture.changeAccessToken("accessToken")
        oAuth2AccessTokenClientPortFixture.changeIdToken(null)

        // when
        val ex = assertThrows<IllegalStateException> {
            oAuth2UserPrincipalLoader.getOAuth2UserPrincipal(
                code, oAuth2ClientRegistration, oAuth2AuthorizationRequest
            )
        }

        // then
        assertThat(ex.message).isEqualTo("Oidc - IdToken not found from OAuth2AccessTokenResponse")
        assertThat(oAuth2UserPrincipalClientPortFixture.callCount).isEqualTo(0)
        assertThat(oidcOAuth2UserPrincipalClientPortFixture.callCount).isEqualTo(0)
    }
}
