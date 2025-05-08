package com.ttasjwi.board.system.user.domain.model

import com.ttasjwi.board.system.user.domain.model.fixture.*
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class OAuth2AuthorizationRequestTest {

    @Nested
    @DisplayName("Naver OAuth2AuthorizationRequest 테스트")
    inner class NaverTest {

        @Test
        @DisplayName("생성 테스트")
        fun createTest() {
            // given
            val oAuth2ClientRegistration = naverOAuth2ClientRegistrationFixture()

            // when
            val oAuth2AuthorizationRequest = OAuth2AuthorizationRequest.create(oAuth2ClientRegistration)

            // then
            assertThat(oAuth2AuthorizationRequest).isNotNull
            assertThat(oAuth2AuthorizationRequest.authorizationUri).isEqualTo(oAuth2ClientRegistration.providerDetails.authorizationUri)
            assertThat(oAuth2AuthorizationRequest.oAuth2ClientRegistrationId).isEqualTo(oAuth2ClientRegistration.registrationId)
            assertThat(oAuth2AuthorizationRequest.responseType).isEqualTo(OAuth2AuthorizationResponseType.CODE)
            assertThat(oAuth2AuthorizationRequest.clientId).isEqualTo(oAuth2ClientRegistration.clientId)
            assertThat(oAuth2AuthorizationRequest.redirectUri).isEqualTo(oAuth2ClientRegistration.redirectUri)
            assertThat(oAuth2AuthorizationRequest.scopes).containsExactlyInAnyOrderElementsOf(oAuth2ClientRegistration.scopes)
            assertThat(oAuth2AuthorizationRequest.state).isNotNull()
            assertThat(oAuth2AuthorizationRequest.pkceParams.codeChallenge).isNotNull()
            assertThat(oAuth2AuthorizationRequest.pkceParams.codeChallengeMethod).isEqualTo(OAuth2AuthorizationRequest.PKCEParams.DEFAULT_CODE_CHALLENGE_METHOD)
            assertThat(oAuth2AuthorizationRequest.pkceParams.codeVerifier).isNotNull()
            assertThat(oAuth2AuthorizationRequest.nonceParams).isNull()
        }


        @Test
        @DisplayName("복원 테스트")
        fun restoreTest() {
            // given
            val oAuth2AuthorizationRequest = naverOAuth2AuthorizationRequestFixture()

            // when
            val restoredOAuth2AuthorizationRequest = OAuth2AuthorizationRequest.restore(
                authorizationUri = oAuth2AuthorizationRequest.authorizationUri,
                oAuth2ClientRegistrationId = oAuth2AuthorizationRequest.oAuth2ClientRegistrationId,
                responseType = oAuth2AuthorizationRequest.responseType.value,
                clientId = oAuth2AuthorizationRequest.clientId,
                redirectUri = oAuth2AuthorizationRequest.redirectUri,
                scopes = oAuth2AuthorizationRequest.scopes,
                state = oAuth2AuthorizationRequest.state,
                codeChallenge = oAuth2AuthorizationRequest.pkceParams.codeChallenge,
                codeChallengeMethod = oAuth2AuthorizationRequest.pkceParams.codeChallengeMethod,
                codeVerifier = oAuth2AuthorizationRequest.pkceParams.codeVerifier,
                nonce = null,
                nonceHash = null,
            )

            // then
            assertThat(restoredOAuth2AuthorizationRequest).isNotNull
            assertThat(restoredOAuth2AuthorizationRequest.authorizationUri).isEqualTo(oAuth2AuthorizationRequest.authorizationUri)
            assertThat(restoredOAuth2AuthorizationRequest.oAuth2ClientRegistrationId).isEqualTo(
                oAuth2AuthorizationRequest.oAuth2ClientRegistrationId
            )
            assertThat(restoredOAuth2AuthorizationRequest.responseType).isEqualTo(oAuth2AuthorizationRequest.responseType)
            assertThat(restoredOAuth2AuthorizationRequest.clientId).isEqualTo(oAuth2AuthorizationRequest.clientId)
            assertThat(restoredOAuth2AuthorizationRequest.redirectUri).isEqualTo(oAuth2AuthorizationRequest.redirectUri)
            assertThat(restoredOAuth2AuthorizationRequest.scopes).containsExactlyInAnyOrderElementsOf(
                oAuth2AuthorizationRequest.scopes
            )
            assertThat(restoredOAuth2AuthorizationRequest.state).isEqualTo(oAuth2AuthorizationRequest.state)
            assertThat(restoredOAuth2AuthorizationRequest.pkceParams.codeChallenge).isEqualTo(oAuth2AuthorizationRequest.pkceParams.codeChallenge)
            assertThat(restoredOAuth2AuthorizationRequest.pkceParams.codeChallengeMethod).isEqualTo(
                oAuth2AuthorizationRequest.pkceParams.codeChallengeMethod
            )
            assertThat(restoredOAuth2AuthorizationRequest.pkceParams.codeVerifier).isEqualTo(oAuth2AuthorizationRequest.pkceParams.codeVerifier)
            assertThat(restoredOAuth2AuthorizationRequest.nonceParams).isNull()
        }

        @Test
        @DisplayName("authorizationRequestUri 테스트")
        fun authorizationRequestUriTest() {
            // given
            val oAuth2AuthorizationRequest = naverOAuth2AuthorizationRequestFixture()

            // when
            val authorizationRequestUri = oAuth2AuthorizationRequest.authorizationRequestUri

            // then
            assertThat(authorizationRequestUri).startsWith(oAuth2AuthorizationRequest.authorizationUri)
            assertThat(authorizationRequestUri).containsSequence("client_id=")
            assertThat(authorizationRequestUri).containsSequence("redirect_uri=")
            assertThat(authorizationRequestUri).containsSequence("response_type=")
            assertThat(authorizationRequestUri).containsSequence("scope=")
            assertThat(authorizationRequestUri).containsSequence("state=")
            assertThat(authorizationRequestUri).containsSequence("code_challenge=")
            assertThat(authorizationRequestUri).containsSequence("code_challenge_method=")
            assertThat(authorizationRequestUri).doesNotContain("nonce=")
        }
    }

    @Nested
    @DisplayName("Kakao OAuth2AuthorizationRequest 테스트")
    inner class KakaoTest {

        @Test
        @DisplayName("생성 테스트")
        fun createTest() {
            // given
            val oAuth2ClientRegistration = kakaoOAuth2ClientRegistrationFixgture()

            // when
            val oAuth2AuthorizationRequest = OAuth2AuthorizationRequest.create(oAuth2ClientRegistration)

            // then
            assertThat(oAuth2AuthorizationRequest).isNotNull
            assertThat(oAuth2AuthorizationRequest.authorizationUri).isEqualTo(oAuth2ClientRegistration.providerDetails.authorizationUri)
            assertThat(oAuth2AuthorizationRequest.oAuth2ClientRegistrationId).isEqualTo(oAuth2ClientRegistration.registrationId)
            assertThat(oAuth2AuthorizationRequest.responseType).isEqualTo(OAuth2AuthorizationResponseType.CODE)
            assertThat(oAuth2AuthorizationRequest.clientId).isEqualTo(oAuth2ClientRegistration.clientId)
            assertThat(oAuth2AuthorizationRequest.redirectUri).isEqualTo(oAuth2ClientRegistration.redirectUri)
            assertThat(oAuth2AuthorizationRequest.scopes).containsExactlyInAnyOrderElementsOf(oAuth2ClientRegistration.scopes)
            assertThat(oAuth2AuthorizationRequest.state).isNotNull()
            assertThat(oAuth2AuthorizationRequest.pkceParams.codeChallenge).isNotNull()
            assertThat(oAuth2AuthorizationRequest.pkceParams.codeChallengeMethod).isEqualTo(OAuth2AuthorizationRequest.PKCEParams.DEFAULT_CODE_CHALLENGE_METHOD)
            assertThat(oAuth2AuthorizationRequest.pkceParams.codeVerifier).isNotNull()
            assertThat(oAuth2AuthorizationRequest.nonceParams!!.nonce).isNotNull()
            assertThat(oAuth2AuthorizationRequest.nonceParams!!.nonceHash).isNotNull()
        }

        @Test
        @DisplayName("복원 테스트")
        fun restoreTest() {
            // given
            val oAuth2AuthorizationRequest = kakaoOAuth2AuthorizationRequestFixture()

            // when
            val restoredOAuth2AuthorizationRequest = OAuth2AuthorizationRequest.restore(
                authorizationUri = oAuth2AuthorizationRequest.authorizationUri,
                oAuth2ClientRegistrationId = oAuth2AuthorizationRequest.oAuth2ClientRegistrationId,
                responseType = oAuth2AuthorizationRequest.responseType.value,
                clientId = oAuth2AuthorizationRequest.clientId,
                redirectUri = oAuth2AuthorizationRequest.redirectUri,
                scopes = oAuth2AuthorizationRequest.scopes,
                state = oAuth2AuthorizationRequest.state,
                codeChallenge = oAuth2AuthorizationRequest.pkceParams.codeChallenge,
                codeChallengeMethod = oAuth2AuthorizationRequest.pkceParams.codeChallengeMethod,
                codeVerifier = oAuth2AuthorizationRequest.pkceParams.codeVerifier,
                nonce = oAuth2AuthorizationRequest.nonceParams?.nonce,
                nonceHash = oAuth2AuthorizationRequest.nonceParams?.nonceHash,
            )

            // then
            assertThat(restoredOAuth2AuthorizationRequest).isNotNull
            assertThat(restoredOAuth2AuthorizationRequest.authorizationUri).isEqualTo(oAuth2AuthorizationRequest.authorizationUri)
            assertThat(restoredOAuth2AuthorizationRequest.oAuth2ClientRegistrationId).isEqualTo(
                oAuth2AuthorizationRequest.oAuth2ClientRegistrationId
            )
            assertThat(restoredOAuth2AuthorizationRequest.responseType).isEqualTo(oAuth2AuthorizationRequest.responseType)
            assertThat(restoredOAuth2AuthorizationRequest.clientId).isEqualTo(oAuth2AuthorizationRequest.clientId)
            assertThat(restoredOAuth2AuthorizationRequest.redirectUri).isEqualTo(oAuth2AuthorizationRequest.redirectUri)
            assertThat(restoredOAuth2AuthorizationRequest.scopes).containsExactlyInAnyOrderElementsOf(
                oAuth2AuthorizationRequest.scopes
            )
            assertThat(restoredOAuth2AuthorizationRequest.state).isEqualTo(oAuth2AuthorizationRequest.state)
            assertThat(restoredOAuth2AuthorizationRequest.pkceParams.codeChallenge).isEqualTo(oAuth2AuthorizationRequest.pkceParams.codeChallenge)
            assertThat(restoredOAuth2AuthorizationRequest.pkceParams.codeChallengeMethod).isEqualTo(
                oAuth2AuthorizationRequest.pkceParams.codeChallengeMethod
            )
            assertThat(restoredOAuth2AuthorizationRequest.pkceParams.codeVerifier).isEqualTo(oAuth2AuthorizationRequest.pkceParams.codeVerifier)
            assertThat(restoredOAuth2AuthorizationRequest.nonceParams!!.nonce).isEqualTo(oAuth2AuthorizationRequest.nonceParams!!.nonce)
            assertThat(restoredOAuth2AuthorizationRequest.nonceParams!!.nonceHash).isEqualTo(oAuth2AuthorizationRequest.nonceParams!!.nonceHash)
        }

        @Test
        @DisplayName("authorizationRequestUri 테스트")
        fun authorizationRequestUriTest() {
            // given
            val oAuth2AuthorizationRequest = kakaoOAuth2AuthorizationRequestFixture()

            // when
            val authorizationRequestUri = oAuth2AuthorizationRequest.authorizationRequestUri

            // then
            assertThat(authorizationRequestUri).startsWith(oAuth2AuthorizationRequest.authorizationUri)
            assertThat(authorizationRequestUri).containsSequence("client_id=")
            assertThat(authorizationRequestUri).containsSequence("redirect_uri=")
            assertThat(authorizationRequestUri).containsSequence("response_type=")
            assertThat(authorizationRequestUri).containsSequence("scope=")
            assertThat(authorizationRequestUri).containsSequence("state=")
            assertThat(authorizationRequestUri).containsSequence("code_challenge=")
            assertThat(authorizationRequestUri).containsSequence("code_challenge_method=")
            assertThat(authorizationRequestUri).containsSequence("nonce=")
        }
    }

    @Nested
    @DisplayName("Google OAuth2AuthorizationRequest 테스트")
    inner class GoogleTest {

        @Test
        @DisplayName("생성 테스트")
        fun createTest() {
            // given
            val oAuth2ClientRegistration = googleOAuth2ClientRegistrationFixture()

            // when
            val oAuth2AuthorizationRequest = OAuth2AuthorizationRequest.create(oAuth2ClientRegistration)

            // then
            assertThat(oAuth2AuthorizationRequest).isNotNull
            assertThat(oAuth2AuthorizationRequest.authorizationUri).isEqualTo(oAuth2ClientRegistration.providerDetails.authorizationUri)
            assertThat(oAuth2AuthorizationRequest.oAuth2ClientRegistrationId).isEqualTo(oAuth2ClientRegistration.registrationId)
            assertThat(oAuth2AuthorizationRequest.responseType).isEqualTo(OAuth2AuthorizationResponseType.CODE)
            assertThat(oAuth2AuthorizationRequest.clientId).isEqualTo(oAuth2ClientRegistration.clientId)
            assertThat(oAuth2AuthorizationRequest.redirectUri).isEqualTo(oAuth2ClientRegistration.redirectUri)
            assertThat(oAuth2AuthorizationRequest.scopes).containsExactlyInAnyOrderElementsOf(oAuth2ClientRegistration.scopes)
            assertThat(oAuth2AuthorizationRequest.state).isNotNull()
            assertThat(oAuth2AuthorizationRequest.pkceParams.codeChallenge).isNotNull()
            assertThat(oAuth2AuthorizationRequest.pkceParams.codeChallengeMethod).isEqualTo(OAuth2AuthorizationRequest.PKCEParams.DEFAULT_CODE_CHALLENGE_METHOD)
            assertThat(oAuth2AuthorizationRequest.pkceParams.codeVerifier).isNotNull()
            assertThat(oAuth2AuthorizationRequest.nonceParams!!.nonce).isNotNull()
            assertThat(oAuth2AuthorizationRequest.nonceParams!!.nonceHash).isNotNull()
        }


        @Test
        @DisplayName("복원 테스트")
        fun restoreTest() {
            // given
            val oAuth2AuthorizationRequest = googleOAuth2AuthorizationRequestFixture()

            // when
            val restoredOAuth2AuthorizationRequest = OAuth2AuthorizationRequest.restore(
                authorizationUri = oAuth2AuthorizationRequest.authorizationUri,
                oAuth2ClientRegistrationId = oAuth2AuthorizationRequest.oAuth2ClientRegistrationId,
                responseType = oAuth2AuthorizationRequest.responseType.value,
                clientId = oAuth2AuthorizationRequest.clientId,
                redirectUri = oAuth2AuthorizationRequest.redirectUri,
                scopes = oAuth2AuthorizationRequest.scopes,
                state = oAuth2AuthorizationRequest.state,
                codeChallenge = oAuth2AuthorizationRequest.pkceParams.codeChallenge,
                codeChallengeMethod = oAuth2AuthorizationRequest.pkceParams.codeChallengeMethod,
                codeVerifier = oAuth2AuthorizationRequest.pkceParams.codeVerifier,
                nonce = oAuth2AuthorizationRequest.nonceParams?.nonce,
                nonceHash = oAuth2AuthorizationRequest.nonceParams?.nonceHash,
            )

            // then
            assertThat(restoredOAuth2AuthorizationRequest).isNotNull
            assertThat(restoredOAuth2AuthorizationRequest.authorizationUri).isEqualTo(oAuth2AuthorizationRequest.authorizationUri)
            assertThat(restoredOAuth2AuthorizationRequest.oAuth2ClientRegistrationId).isEqualTo(
                oAuth2AuthorizationRequest.oAuth2ClientRegistrationId
            )
            assertThat(restoredOAuth2AuthorizationRequest.responseType).isEqualTo(oAuth2AuthorizationRequest.responseType)
            assertThat(restoredOAuth2AuthorizationRequest.clientId).isEqualTo(oAuth2AuthorizationRequest.clientId)
            assertThat(restoredOAuth2AuthorizationRequest.redirectUri).isEqualTo(oAuth2AuthorizationRequest.redirectUri)
            assertThat(restoredOAuth2AuthorizationRequest.scopes).containsExactlyInAnyOrderElementsOf(
                oAuth2AuthorizationRequest.scopes
            )
            assertThat(restoredOAuth2AuthorizationRequest.state).isEqualTo(oAuth2AuthorizationRequest.state)
            assertThat(restoredOAuth2AuthorizationRequest.pkceParams.codeChallenge).isEqualTo(oAuth2AuthorizationRequest.pkceParams.codeChallenge)
            assertThat(restoredOAuth2AuthorizationRequest.pkceParams.codeChallengeMethod).isEqualTo(
                oAuth2AuthorizationRequest.pkceParams.codeChallengeMethod
            )
            assertThat(restoredOAuth2AuthorizationRequest.pkceParams.codeVerifier).isEqualTo(oAuth2AuthorizationRequest.pkceParams.codeVerifier)
            assertThat(restoredOAuth2AuthorizationRequest.nonceParams!!.nonce).isEqualTo(oAuth2AuthorizationRequest.nonceParams!!.nonce)
            assertThat(restoredOAuth2AuthorizationRequest.nonceParams!!.nonceHash).isEqualTo(oAuth2AuthorizationRequest.nonceParams!!.nonceHash)
        }

        @Test
        @DisplayName("authorizationRequestUri 테스트")
        fun authorizationRequestUriTest() {
            // given
            val oAuth2AuthorizationRequest = googleOAuth2AuthorizationRequestFixture()

            // when
            val authorizationRequestUri = oAuth2AuthorizationRequest.authorizationRequestUri

            // then
            assertThat(authorizationRequestUri).startsWith(oAuth2AuthorizationRequest.authorizationUri)
            assertThat(authorizationRequestUri).containsSequence("client_id=")
            assertThat(authorizationRequestUri).containsSequence("redirect_uri=")
            assertThat(authorizationRequestUri).containsSequence("response_type=")
            assertThat(authorizationRequestUri).containsSequence("scope=")
            assertThat(authorizationRequestUri).containsSequence("state=")
            assertThat(authorizationRequestUri).containsSequence("code_challenge=")
            assertThat(authorizationRequestUri).containsSequence("code_challenge_method=")
            assertThat(authorizationRequestUri).containsSequence("nonce=")
        }
    }

    @Nested
    @DisplayName("matchesNonce: Nonce 값을 비교하여 올바르면 true, 올바르지 않으면 false 반환")
    inner class MatchesNonceTest {

        @Test
        @DisplayName("올바르면 true")
        fun successTest() {
            // given
            val oAuth2AuthorizationRequest = googleOAuth2AuthorizationRequestFixture()

            // when
            val idTokenNonce = oAuth2AuthorizationRequest.nonceParams!!.nonce

            // then
            assertThat(oAuth2AuthorizationRequest.matchesNonce(idTokenNonce)).isTrue()
        }


        @Test
        @DisplayName("올바르지 않으면 false")
        fun failureTest() {
            // given
            val oAuth2AuthorizationRequest = googleOAuth2AuthorizationRequestFixture()

            // when
            val invalidIdTokenNonce = oAuth2AuthorizationRequest.nonceParams!!.nonce.substring(1)

            // then
            assertThat(oAuth2AuthorizationRequest.matchesNonce(invalidIdTokenNonce)).isFalse()
        }
    }
}
