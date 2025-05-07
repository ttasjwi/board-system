package com.ttasjwi.board.system.user.domain.processor

import com.ttasjwi.board.system.common.time.fixture.appDateTimeFixture
import com.ttasjwi.board.system.user.domain.dto.SocialServiceAuthorizationCommand
import com.ttasjwi.board.system.user.domain.exception.UnsupportedSocialServiceIdException
import com.ttasjwi.board.system.user.domain.port.fixture.OAuth2AuthorizationRequestPersistencePortFixture
import com.ttasjwi.board.system.user.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SocialServiceAuthorizationProcessorTest {

    private lateinit var processor: SocialServiceAuthorizationProcessor
    private lateinit var oAuth2AuthorizationRequestPersistencePortFixture: OAuth2AuthorizationRequestPersistencePortFixture

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        processor = container.socialServiceAuthorizationProcessor
        oAuth2AuthorizationRequestPersistencePortFixture = container.oAuth2AuthorizationRequestPersistencePortFixture
    }


    @Test
    @DisplayName("소셜서비스 인가요청 생성 테스트")
    fun testSuccess() {
        // given
        val command = SocialServiceAuthorizationCommand(
            oAuth2ClientRegistrationId = "google",
            currentTime = appDateTimeFixture(minute = 8)
        )

        // when
        val oAuth2AuthorizationRequest = processor.generateOAuth2AuthorizationRequest(command)

        // then
        val findOAuth2AuthorizationRequest = oAuth2AuthorizationRequestPersistencePortFixture.read(oAuth2AuthorizationRequest.state)
        
        assertThat(oAuth2AuthorizationRequest).isNotNull
        assertThat(findOAuth2AuthorizationRequest).isNotNull
    }


    @Test
    @DisplayName("지원하지 않는 소셜서비스 아이디일 경우 예외 발생")
    fun testSocialServiceNotFound() {
        // given
        val command = SocialServiceAuthorizationCommand(
            oAuth2ClientRegistrationId = "facebook",
            currentTime = appDateTimeFixture(minute = 13)
        )

        // when
        assertThrows<UnsupportedSocialServiceIdException> {
            processor.generateOAuth2AuthorizationRequest(command)
        }
    }

}
