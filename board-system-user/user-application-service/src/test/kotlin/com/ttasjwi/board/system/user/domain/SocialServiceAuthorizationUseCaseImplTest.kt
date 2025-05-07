package com.ttasjwi.board.system.user.domain

import com.ttasjwi.board.system.user.domain.port.OAuth2AuthorizationRequestPersistencePort
import com.ttasjwi.board.system.user.domain.test.support.TestContainer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.net.URLDecoder

@DisplayName("[user-application-service] SocialServiceAuthorizationUseCaseImpl 테스트")
class SocialServiceAuthorizationUseCaseImplTest {

    private lateinit var useCase: SocialServiceAuthorizationUseCase
    private lateinit var oAuth2AuthorizationRequestPersistencePort: OAuth2AuthorizationRequestPersistencePort

    @BeforeEach
    fun setup() {
        val container = TestContainer.create()
        useCase = container.socialServiceAuthorizationUseCase
        oAuth2AuthorizationRequestPersistencePort = container.oAuth2AuthorizationRequestPersistencePortFixture
    }

    @Test
    @DisplayName("Uri 생성 테스트")
    fun testSuccess() {
        // given
        val socialServiceId = "naver"

        // when
        val authorizationRequestUri = useCase.generateAuthorizationRequestUri(socialServiceId)


        // then
        val decodedUrl = URLDecoder.decode(authorizationRequestUri, "UTF-8")
        val state = decodedUrl.substringAfter("state=")
            .substringBefore("&")
        val findOAuth2AuthorizationRequest = oAuth2AuthorizationRequestPersistencePort.read(state)!!

        assertThat(authorizationRequestUri).isEqualTo(findOAuth2AuthorizationRequest.authorizationRequestUri)
    }
}
