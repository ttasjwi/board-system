package com.ttasjwi.board.system

import com.ttasjwi.board.system.auth.domain.external.ExternalAccessTokenManager
import com.ttasjwi.board.system.auth.domain.external.ExternalRefreshTokenManager
import com.ttasjwi.board.system.auth.domain.service.fixture.AccessTokenManagerFixture
import com.ttasjwi.board.system.core.time.fixture.TimeManagerFixture
import com.ttasjwi.board.system.member.domain.external.ExternalPasswordHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest
@AutoConfigureMockMvc
abstract class SecurityTest {

    @Autowired
    protected lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var clientRegistrationRepository: ClientRegistrationRepository

    @MockBean
    private lateinit var authorizationRequestRepository: AuthorizationRequestRepository<OAuth2AuthorizationRequest>

    @Autowired
    protected lateinit var accessTokenManagerFixture: AccessTokenManagerFixture

    @Autowired
    protected lateinit var timeManagerFixture: TimeManagerFixture

    @Autowired
    protected lateinit var externalPasswordHandler: ExternalPasswordHandler

    @Autowired
    protected lateinit var externalRefreshTokenManager: ExternalRefreshTokenManager

    @Autowired
    protected lateinit var externalAccessTokenManager: ExternalAccessTokenManager
}
