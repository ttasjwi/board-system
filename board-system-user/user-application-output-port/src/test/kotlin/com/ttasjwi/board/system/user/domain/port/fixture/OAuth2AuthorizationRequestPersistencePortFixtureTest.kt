package com.ttasjwi.board.system.user.domain.port.fixture

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.user.domain.model.fixture.googleOAuth2AuthorizationRequestFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("[user-domain] OAuth2AuthorizationRequestPersistencePortFixture 테스트")
class OAuth2AuthorizationRequestPersistencePortFixtureTest {

    private lateinit var oAuth2AuthorizationRequestPersistencePortFixture: OAuth2AuthorizationRequestPersistencePortFixture

    @BeforeEach
    fun setup() {
        oAuth2AuthorizationRequestPersistencePortFixture = OAuth2AuthorizationRequestPersistencePortFixture()
    }

    @Test
    @DisplayName("저장 후 조회 테스트")
    fun saveAndReadTest() {
        // given
        val oAuth2AuthorizationRequest = googleOAuth2AuthorizationRequestFixture()

        // when
        oAuth2AuthorizationRequestPersistencePortFixture.save(
            oAuth2AuthorizationRequest,
            AppDateTime.now().plusMinutes(5)
        )

        // then
        val findOAuth2AuthorizationRequest =
            oAuth2AuthorizationRequestPersistencePortFixture.read(oAuth2AuthorizationRequest.state)!!

        assertThat(findOAuth2AuthorizationRequest).isNotNull
        assertThat(findOAuth2AuthorizationRequest.authorizationUri).isEqualTo(oAuth2AuthorizationRequest.authorizationUri)
        assertThat(findOAuth2AuthorizationRequest.oAuth2ClientRegistrationId).isEqualTo(oAuth2AuthorizationRequest.oAuth2ClientRegistrationId)
        assertThat(findOAuth2AuthorizationRequest.responseType).isEqualTo(oAuth2AuthorizationRequest.responseType)
        assertThat(findOAuth2AuthorizationRequest.clientId).isEqualTo(oAuth2AuthorizationRequest.clientId)
        assertThat(findOAuth2AuthorizationRequest.redirectUri).isEqualTo(oAuth2AuthorizationRequest.redirectUri)
        assertThat(findOAuth2AuthorizationRequest.scopes).containsExactlyInAnyOrderElementsOf(
            oAuth2AuthorizationRequest.scopes
        )
        assertThat(findOAuth2AuthorizationRequest.state).isEqualTo(oAuth2AuthorizationRequest.state)
        assertThat(findOAuth2AuthorizationRequest.pkceParams.codeChallenge).isEqualTo(oAuth2AuthorizationRequest.pkceParams.codeChallenge)
        assertThat(findOAuth2AuthorizationRequest.pkceParams.codeChallengeMethod).isEqualTo(
            oAuth2AuthorizationRequest.pkceParams.codeChallengeMethod
        )
        assertThat(findOAuth2AuthorizationRequest.pkceParams.codeVerifier).isEqualTo(oAuth2AuthorizationRequest.pkceParams.codeVerifier)
        assertThat(findOAuth2AuthorizationRequest.nonceParams!!.nonce).isEqualTo(oAuth2AuthorizationRequest.nonceParams!!.nonce)
        assertThat(findOAuth2AuthorizationRequest.nonceParams!!.nonceHash).isEqualTo(oAuth2AuthorizationRequest.nonceParams!!.nonceHash)
    }

    @Test
    @DisplayName("저장 후 삭제 테스트")
    fun saveAndRemoveTest() {
        // given
        val oAuth2AuthorizationRequest = googleOAuth2AuthorizationRequestFixture()
        oAuth2AuthorizationRequestPersistencePortFixture.save(
            oAuth2AuthorizationRequest,
            AppDateTime.now().plusMinutes(5)
        )

        // when
        val removedOAuth2AuthorizationRequest =
            oAuth2AuthorizationRequestPersistencePortFixture.remove(oAuth2AuthorizationRequest.state)!!

        // then
        val findOAuth2AuthorizationRequest =
            oAuth2AuthorizationRequestPersistencePortFixture.read(oAuth2AuthorizationRequest.state)

        assertThat(removedOAuth2AuthorizationRequest).isNotNull
        assertThat(removedOAuth2AuthorizationRequest.authorizationUri).isEqualTo(oAuth2AuthorizationRequest.authorizationUri)
        assertThat(removedOAuth2AuthorizationRequest.oAuth2ClientRegistrationId).isEqualTo(
            oAuth2AuthorizationRequest.oAuth2ClientRegistrationId
        )
        assertThat(removedOAuth2AuthorizationRequest.responseType).isEqualTo(oAuth2AuthorizationRequest.responseType)
        assertThat(removedOAuth2AuthorizationRequest.clientId).isEqualTo(oAuth2AuthorizationRequest.clientId)
        assertThat(removedOAuth2AuthorizationRequest.redirectUri).isEqualTo(oAuth2AuthorizationRequest.redirectUri)
        assertThat(removedOAuth2AuthorizationRequest.scopes).containsExactlyInAnyOrderElementsOf(
            oAuth2AuthorizationRequest.scopes
        )
        assertThat(removedOAuth2AuthorizationRequest.state).isEqualTo(oAuth2AuthorizationRequest.state)
        assertThat(removedOAuth2AuthorizationRequest.pkceParams.codeChallenge).isEqualTo(oAuth2AuthorizationRequest.pkceParams.codeChallenge)
        assertThat(removedOAuth2AuthorizationRequest.pkceParams.codeChallengeMethod).isEqualTo(
            oAuth2AuthorizationRequest.pkceParams.codeChallengeMethod
        )
        assertThat(removedOAuth2AuthorizationRequest.pkceParams.codeVerifier).isEqualTo(oAuth2AuthorizationRequest.pkceParams.codeVerifier)
        assertThat(removedOAuth2AuthorizationRequest.nonceParams!!.nonce).isEqualTo(oAuth2AuthorizationRequest.nonceParams!!.nonce)
        assertThat(removedOAuth2AuthorizationRequest.nonceParams!!.nonceHash).isEqualTo(oAuth2AuthorizationRequest.nonceParams!!.nonceHash)

        assertThat(findOAuth2AuthorizationRequest).isNull()
    }
}
