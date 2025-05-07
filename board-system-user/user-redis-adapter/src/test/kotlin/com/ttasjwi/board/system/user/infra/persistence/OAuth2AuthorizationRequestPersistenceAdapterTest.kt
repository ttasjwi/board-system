package com.ttasjwi.board.system.user.infra.persistence

import com.ttasjwi.board.system.common.time.AppDateTime
import com.ttasjwi.board.system.user.domain.model.fixture.googleOAuth2AuthorizationRequestFixture
import com.ttasjwi.board.system.user.domain.model.fixture.kakaoOAuth2AuthorizationRequestFixture
import com.ttasjwi.board.system.user.domain.model.fixture.naverOAuth2AuthorizationRequestFixture
import com.ttasjwi.board.system.user.infra.test.UserRedisAdapterTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


@DisplayName("[user-redis-adapter] OAuth2AuthorizationRequestPersistenceAdapter 테스트")
class OAuth2AuthorizationRequestPersistenceAdapterTest : UserRedisAdapterTest() {

    @Nested
    @DisplayName("Google 테스트")
    inner class GoogleTest {

        @Test
        @DisplayName("저장 후 조회 테스트")
        fun saveAndReadTest() {
            // given
            val oAuth2AuthorizationRequest = googleOAuth2AuthorizationRequestFixture()

            // when
            oAuth2AuthorizationRequestPersistenceAdapter.save(
                oAuth2AuthorizationRequest,
                AppDateTime.now().plusMinutes(5)
            )

            // then
            val findOAuth2AuthorizationRequest =
                oAuth2AuthorizationRequestPersistenceAdapter.read(oAuth2AuthorizationRequest.state)!!

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
            oAuth2AuthorizationRequestPersistenceAdapter.save(
                oAuth2AuthorizationRequest,
                AppDateTime.now().plusMinutes(5)
            )

            // when
            val removedOAuth2AuthorizationRequest =
                oAuth2AuthorizationRequestPersistenceAdapter.remove(oAuth2AuthorizationRequest.state)!!

            // then
            val findOAuth2AuthorizationRequest =
                oAuth2AuthorizationRequestPersistenceAdapter.read(oAuth2AuthorizationRequest.state)

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

    @Nested
    @DisplayName("Kakao 테스트")
    inner class KakaoTest {

        @Test
        @DisplayName("저장 후 조회 테스트")
        fun saveAndReadTest() {
            // given
            val oAuth2AuthorizationRequest = kakaoOAuth2AuthorizationRequestFixture()

            // when
            oAuth2AuthorizationRequestPersistenceAdapter.save(
                oAuth2AuthorizationRequest,
                AppDateTime.now().plusMinutes(5)
            )

            // then
            val findOAuth2AuthorizationRequest =
                oAuth2AuthorizationRequestPersistenceAdapter.read(oAuth2AuthorizationRequest.state)!!

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
            val oAuth2AuthorizationRequest = kakaoOAuth2AuthorizationRequestFixture()
            oAuth2AuthorizationRequestPersistenceAdapter.save(
                oAuth2AuthorizationRequest,
                AppDateTime.now().plusMinutes(5)
            )

            // when
            val removedOAuth2AuthorizationRequest =
                oAuth2AuthorizationRequestPersistenceAdapter.remove(oAuth2AuthorizationRequest.state)!!

            // then
            val findOAuth2AuthorizationRequest =
                oAuth2AuthorizationRequestPersistenceAdapter.read(oAuth2AuthorizationRequest.state)

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


    @Nested
    @DisplayName("Naver 테스트")
    inner class NaverTest {

        @Test
        @DisplayName("저장 후 조회 테스트")
        fun saveAndReadTest() {
            // given
            val oAuth2AuthorizationRequest = naverOAuth2AuthorizationRequestFixture()

            // when
            oAuth2AuthorizationRequestPersistenceAdapter.save(
                oAuth2AuthorizationRequest,
                AppDateTime.now().plusMinutes(5)
            )

            // then
            val findOAuth2AuthorizationRequest =
                oAuth2AuthorizationRequestPersistenceAdapter.read(oAuth2AuthorizationRequest.state)!!

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
            assertThat(findOAuth2AuthorizationRequest.nonceParams).isNull()
        }

        @Test
        @DisplayName("저장 후 삭제 테스트")
        fun saveAndRemoveTest() {
            // given
            val oAuth2AuthorizationRequest = naverOAuth2AuthorizationRequestFixture()
            oAuth2AuthorizationRequestPersistenceAdapter.save(
                oAuth2AuthorizationRequest,
                AppDateTime.now().plusMinutes(5)
            )

            // when
            val removedOAuth2AuthorizationRequest =
                oAuth2AuthorizationRequestPersistenceAdapter.remove(oAuth2AuthorizationRequest.state)!!

            // then
            val findOAuth2AuthorizationRequest =
                oAuth2AuthorizationRequestPersistenceAdapter.read(oAuth2AuthorizationRequest.state)

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
            assertThat(removedOAuth2AuthorizationRequest.nonceParams).isNull()
            assertThat(findOAuth2AuthorizationRequest).isNull()
        }
    }


    @Test
    @DisplayName("없는 state 로 조회할 경우 조회 실패")
    fun notFoundTest() {
        // given
        val state = "adfadfadf"

        // when
        val findOAuth2AuthorizationRequest = oAuth2AuthorizationRequestPersistenceAdapter.read(state)

        // then
        assertThat(findOAuth2AuthorizationRequest).isNull()
    }

    @Test
    @DisplayName("없는 state 로 삭제할 경우 null 만 반환됨")
    fun notDeleteTest() {
        // given
        val state = "adfadfadf"

        // when
        val removedOAuth2AuthorizationRequest = oAuth2AuthorizationRequestPersistenceAdapter.remove(state)

        // then
        assertThat(removedOAuth2AuthorizationRequest).isNull()
    }
}
